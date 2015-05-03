ctrls.controller("giftCenterRedeemGiftController", ['$rootScope','$scope','Student'
                                          ,'GetGifts','GetGiftsExNum','ExchangeGifts'
  ,function ($rootScope,$scope, Student,GetGifts,GetGiftsExNum,ExchangeGifts) {
    var page = 0;
    var size = 6;
    var pageSize = 1024;
    var stateTexts = { more : "加载更多",loading: "更多加载中...",nomore: "没有了"};
    $scope.loading = stateTexts.loading;

    $scope.statuses = [{
        id: 0,
        name:"全部类型"
    }, {
        id: 1,
        name: "TYPE_1",
        callback: $scope.test
    }, {
        id: 2,
        name: "TYPE_2",
        callback: $scope.test
    }];
    $scope.selected_status = 0;

    $scope.redeemState = 0;
    // $scope.currentCoin = $rootScope.student.
    var student = $rootScope.student;
    console.log('student :')
    console.log(student);
    console.log('statistic :'+student.statistic);

    // var currentCoin = student.statistic.coin;
    // console.log(currentCoin);
    $scope.userCoin = null;
    $scope.gifts = new Array(0);
    var all = new Array(0);

    Student.get({id:$rootScope.id},function(data){
      $scope.userCoin = data.statistic.coin;
      console.log($scope.userCoin);
      $scope.getAllGifts();
      })

    $scope.getAllGifts = function(){
        $scope.gifts = new Array(0);
        GetGifts.get({page:page,size:pageSize},function(data){
        console.log(data);
        var content = data.content;
        all = content;
        if($scope.userCoin>=0){
          if($scope.redeemState != 0){
            all = new Array(0);
            redeemStateFilter(all,content,$scope.userCoin);
          }
          $scope.loading = setLoadingState(all,size);
          initLoad($scope.gifts,all,size);
        }
      })};

    // $scope.getAllGifts();
    $scope.giftsLoadingMore = function(){
      if($scope.loading === stateTexts.nomore)
        return;
      if(loadingMore($scope.gifts,all,size))
        $scope.loading = stateTexts.more;
      else
        $scope.loading = stateTexts.nomore;
      }
    // $scope.giftsLoadingMore = function(){
    //   if($scope.loading === stateTexts.nomore)
    //     return;
    //   if(loadingMore($scope.gifts,allGifts.content,size))
    //     $scope.loading = stateTexts.more;
    //   else
    //     $scope.loading = stateTexts.nomore;
    // }

  $scope.quantity = 1;
  $scope.addQuantity = function(){
    $scope.quantity++;
    // $scope.isValid();
  }
  $scope.minusQuantity = function(){
    $scope.quantity--;
    // $scope.isValid();1
  }
  $scope.isValid = function(){
    if($scope.quantity>0)
      return true;
    else return false;
  }

  $scope.getGiftId = function(gift){
    console.log(gift);
    $scope.giftId = gift.id;
  }


  $scope.redeemGift = new ExchangeGifts();
  $scope.exchangeGifts  = function(gift){
    $scope.redeemGift.$update({studentId:$rootScope.id,giftId:$scope.giftId,count:$scope.quantity},function(){
      $rootScope.modal ={
          title: "兑换成功",
          content:''
        };
      $('#alert-modal').modal();
    },function(error){
      $rootScope.modal ={
          title: "兑换失败",
          content:'财富不够'
        };
      $('#alert-modal').modal();
    });
  }

  var initLoad = function(part,all,size){
    if(typeof(all)=== 'undefined')
      return 0;
    for(var i=0; i<all.length&&i<size;i++)
      part.push(all[i]);
    // console.log(part);
    return part.length;
  }

  var setLoadingState = function(arr,size){
    if(arr.length<=size)
      return stateTexts.nomore;
    else if(arr.length>size)
      return stateTexts.more;
  };

  var loadingMore = function(part,all,size){
    var allNum = all.length;
    for(var i=0;i<size;i++){
      if(part.length<allNum)
        part.push(all[part.length]);
      else
        return false;
    }
    return true;
  }

  $scope.giftTypeFilter = function(e){
    // console.log($scope.statuses[$scope.selected_status].name);
    if($scope.selected_status>0)
      return e.giftType == $scope.statuses[$scope.selected_status].name;
    else
      return e;
    };

  // var student = Student.get({id:$rootScope.id},function(){
  //   $scope.userCoin = student.statistic.coin;
  //   console.log($scope.userCoin);
  //   var redeemStateFilter = function(all,content){
  //       if(typeof(content) === 'undefined'){
  //         console.log('content is undefined');
  //         return;
  //       }
  //
  //       if($scope.redeemState === 0 ){
  //           all = content;
  //           return;
  //       }
  //       else if($scope.redeemState === 1){
  //         for(var i=0;i<content.length;i++)
  //           if(content[i].coin <= $scope.userCoin)
  //             all.push(content[i]);
  //       }
  //       else if($scope.redeemState === 2){
  //         for(var j=0;j<content.length;j++)
  //           if(content[j].coin > $scope.userCoin)
  //             all.push(content[j]);
  //       }
  //     }
  //   })
    var redeemStateFilter = function(all,content,userCoin){
        if(typeof(content) === 'undefined'){
          console.log('content is undefined');
          return;
        }
        if($scope.redeemState === '1'){
          for(var i=0;i<content.length;i++)
            if(content[i].coin <= userCoin)
              all.push(content[i]);
        }
        else if($scope.redeemState === '2'){
          for(var j=0;j<content.length;j++)
            if(content[j].coin > userCoin)
              all.push(content[j]);
        }
        return;
      }
    // $scope.redeemStateFilter = function(e){
    //   // console.log($scope.redeemState);
    //   if($scope.redeemState === 0 )
    //     return e;
    //   else if($scope.redeemState === 1){
    //     return e.coin <= $scope.userCoin;
    //   }
    //   else if($scope.redeemState === 2){
    //     return e.coin > $scope.userCoin;
    //   }
    // }

}]);

//
// ctrls.filter('formatGiftType',function(){
//   return function(data){
//
//   }
