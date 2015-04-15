//readingCenterTestSubjectiveCtrl.js

ctrls.controller("readingCenterTestSubjectiveController", ['$scope', '$rootScope', 'ThinkExam', function ($scope, $rootScope, ThinkExam) {
	if ($rootScope.exam == undefined) {
	window.location.href="/protype/index.html#/readingCenter/myBookshelf";
	};
	$scope.bookName = $rootScope.exam.bookName;

	ThinkExam.get($rootScope.exam.bookId, function(data){
		$scope.questions = data;
	})
}]);