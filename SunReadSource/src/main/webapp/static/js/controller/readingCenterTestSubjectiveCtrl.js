//readingCenterTestSubjectiveCtrl.js

ctrls.controller("readingCenterTestSubjectiveController", ['$scope', '$rootScope', 'ThinkExam', function ($scope, $rootScope, ThinkExam) {
	if ($rootScope.exam == undefined) {
	window.location.href="/protype/index.html#/readingCenter/myBookshelf";
	};
	$scope.bookName = $rootScope.exam.bookName;

	ThinkExam.get($rootScope.exam.bookId, function(data){
		$scope.questions = data;

		$scope.finishExam = function(){
			$scope.myAnswer = {
				"bookId": $rootScope.exam.bookId,
				"studentId": $rootScope.id,
				"examType": "THINK"
			};
			$scope.myAnswer.answers = new Array();
			$scope.myAnswer.questions = $scope.questions;
			for (var i = 0; i < $scope.questions.length; i++) {
				answer = {};
				answer.question = $scope.questions[i];
				answer.studentId = $rootScope.id;
				answer.content = $scope.questions[i].content;

				$scope.myAnswer.answers[i] = answer;
			};
			ThinkExam.submitExam($scope.myAnswer, function(examData){
				console.log(examData);
			})
		}
	})
}]);