//readingCenterAddBookAdvancedSearchCtrl.js

ctrls.controller("readingCenterAddBookAdvancedSearchController", ['$scope', function ($scope) {
	$scope.showCondition = true;
	
	$scope.search = function(){
		$scope.showCondition = false;
	}
}]);