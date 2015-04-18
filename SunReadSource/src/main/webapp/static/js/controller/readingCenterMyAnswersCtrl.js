//readingCenterMyAnswersCtrl.js

ctrls.controller("readingCenterMyAnswersController", ['$scope', '$rootScope', 'ThinkExam', function ($scope, $rootScope, ThinkExam) {
	$scope.selectThinkExam = $rootScope.selectThinkExam;
	ThinkExam.getAllInfo($rootScope.id, function(data){
		var model = data.examDTOs[0].subjectiveAnswers;
		$scope.questions = new Array();
		var question = {};
		for(var i = 0; i < model.length; i++){
			if (i == 0) {
				question.array = new Array();
				question.type = model[i].questionType;
				question.array.push(model[i]);
			}
		}
	}
}]);