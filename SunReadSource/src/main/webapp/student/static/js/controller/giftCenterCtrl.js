ctrls.controller("giftCenterController", ['$rootScope','$scope', 'GetGifts','GetGiftsExNum',
	function ($rootScope,$scope, GetGifts,GetGiftsExNum) {
		var page = 0;
		var size = 6;
		var pageSize = 1024;
		var stateTexts = { more : "加载更多",loading: "更多加载中...",nomore: "没有了"};
		$scope.loading = stateTexts.loading;
		// $scope.gifts = GetGifts.get({page:page,size:size},function(){
		// 	console.log($scope.gifts);
		// })

		$scope.gifts = new Array(0);
		var allGifts = GetGiftsExNum.get({id:$rootScope.id, page:page,size:pageSize},function(){
			// console.log($scope.gifts);
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
  }]);

	ctrls.filter('formatDeliver',function(){
		return function(input){
			if(input == 'DELIVERED')
				return '已发货';
			else
				return '已送达';
		}
	})
