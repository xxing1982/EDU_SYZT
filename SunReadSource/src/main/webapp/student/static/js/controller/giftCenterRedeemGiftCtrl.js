ctrls.controller("giftCenterRedeemGiftController", ['$rootScope','$scope'
                                          ,'GetGifts','GetGiftsExNum','ExchangeGifts'
  ,function ($rootScope,$scope, GetGifts,GetGiftsExNum,ExchangeGifts) {
    var page = 0;
    var size = 6;

    $scope.gifts = GetGifts.get({page:page,size:size},function(){
    	console.log($scope.gifts);
    })

    // $scope.gifts = GetGiftsExNum.get({id:1, page:page,size:size},function(){
    //   console.log($scope.gifts);
    //   if($scope.gifts.content.exchanges==="exchanges")
    //     $scope.gifts.content.status==="已发货";
    //
    // })


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

  $scope.exchangeGifts = function(gift){
    ExchangeGifts.update({},function(){
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
      name: "文具",
      callback: $scope.test
  }, {
      id: 2,
      name: "饰品",
      callback: $scope.test
  }];
  $scope.selected_status = 0;
}]);
