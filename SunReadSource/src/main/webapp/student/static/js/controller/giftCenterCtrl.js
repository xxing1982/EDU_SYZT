ctrls.controller("giftCenterController", ['$rootScope','$scope', 'GetGifts','GetGiftsExNum',
	function ($rootScope,$scope, GetGifts,GetGiftsExNum) {
		var page = 0;
		var size = 3;
		// $scope.gifts = GetGifts.get({page:page,size:size},function(){
		// 	console.log($scope.gifts);
		// })

		$scope.gifts = GetGiftsExNum.get({id:1, page:page,size:size},function(){
			console.log($scope.gifts);
			if($scope.gifts.content.exchanges==="exchanges")
				$scope.gifts.content.status==="已发货";
				
		})


  }]);
