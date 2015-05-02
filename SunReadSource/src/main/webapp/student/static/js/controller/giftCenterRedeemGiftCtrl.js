ctrls.controller("giftCenterRedeemGiftController", ['$rootScope','$scope'
                                          ,'GetGifts','GetGiftsExNum','ExchangeGifts'
  ,function ($rootScope,$scope, GetGifts,GetGiftsExNum,ExchangeGifts) {
    var page = 0;
    var size = 1;
    var pageSize = 1024;
    var stateTexts = { more : "加载更多",loading: "更多加载中...",nomore: "没有了"};
    $scope.loading = stateTexts.loading;

    $scope.redeemState = 0;
    // $scope.currentCoin = $rootScope.student.
    var student = $rootScope.student;
    console.log('student :')
    console.log(student);
    console.log('statistic :'+student.statistic);

    // var currentCoin = student.statistic.coin;
    // console.log(currentCoin);
    $scope.gifts = new Array(0);

    var allGifts = GetGifts.get({page:page,size:pageSize},function(){
      console.log(allGifts);
      var content = allGifts.content;
      $scope.loading = setLoadingState(content,size);
      initLoad($scope.gifts,content,size);
    })

    $scope.giftsLoadingMore = function(){
      if($scope.loading === stateTexts.nomore)
        return;
      if(loadingMore($scope.gifts,allGifts.content,size))
        $scope.loading = stateTexts.more;
      else
        $scope.loading = stateTexts.nomore;
    }

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

    $scope.redeemStateFilter = function(e){
      console.log($scope.redeemState);

    }
}]);

//
// ctrls.filter('formatGiftType',function(){
//   return function(data){
//
//   }
