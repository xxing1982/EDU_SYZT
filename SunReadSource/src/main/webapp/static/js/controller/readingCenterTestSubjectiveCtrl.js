//readingCenterTestSubjectiveCtrl.js

ctrls.controller("readingCenterTestSubjectiveController", ['$scope', '$rootScope', function ($scope, $rootScope) {
	if ($rootScope.exam == undefined) {
	window.location.href="/protype/index.html#/readingCenter/myBookshelf";
	};
	$scope.bookName = $rootScope.exam.bookName;
}]);