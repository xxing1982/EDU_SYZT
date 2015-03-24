//readingCenterMyEvaluatingCtrl.js

ctrls.controller("readingCenterMyEvaluatingController", ['$scope', function ($scope) {
	function hideAllTabs(){
		$scope.isCertification = false;
		$scope.isWord = false;
		$scope.isThinking = false;
	}
	$scope.showCertification = function(){
		hideAllTabs();
		$scope.isCertification = true;
	}
	$scope.showWord = function(){
		hideAllTabs();
		$scope.isWord = true;
	}
	$scope.showThinking = function(){
		hideAllTabs();
		$scope.isThinking = true;
	}
	$scope.isCertification = true;
}]);