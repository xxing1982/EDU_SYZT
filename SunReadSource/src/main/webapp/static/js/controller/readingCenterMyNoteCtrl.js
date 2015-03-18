//readingCenterMyNoteCtrl.js

ctrls.controller("readingCenterMyNoteController", ['$scope', function ($scope) {
	$scope.showMust = true;
	$scope.showRepley = true;

	$scope.IsShow = function(){
		$scope.showRepley = !$scope.showRepley;
	}
}]);