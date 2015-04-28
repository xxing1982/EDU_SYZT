//readingCenterMyAnswersCtrl.js

ctrls.controller("readingCenterMyAnswersController", ['$scope', '$rootScope', 'ThinkExam', function ($scope, $rootScope, ThinkExam) {
	//$rootScope.selectThinkExam;
	$scope.name = $rootScope.selectThinkExam.name;
	var data = $rootScope.selectThinkExam.answers;
	var map = {};
	for(var i = 0; i < data.length; i++){
		if (!(data[i].questionType in map)) {
			if(map[data[i].question.questionType] == undefined){
				map[data[i].question.questionType] = new Array();
			}
			map[data[i].question.questionType].push(data[i]);
		}
		else{
			map[data[i].question.questionType].push(data[i]);
		}
	}
	$scope.answers = new Array();
	for(value in map){
		var answer = {};
		answer.type = value;
		answer.array = map[value];
		$scope.answers.push(answer);
	}
}]);