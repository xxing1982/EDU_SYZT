//prizeCenterCtrl.js

ctrls.controller("prizeCenterController", ['$scope', 'Question', 'Option',
	function ($scope, Question, Option) {
		$scope.prizeCenter = '奖品中心';

		//var question = new Question();
		var question = {
			"topic": "主题1",
			"bookId": 1,
			"options": [
			{
				"tag":"A",
				"content":"正确答案"
			},
			{
				"tag":"B",
				"content":"错误答案1"
			},
			{
				"tag":"C",
				"content":"错误答案2"
			},
			{
				"tag":"D",
				"content":"错误答案3"
			}
			],
			"objectiveType": "WORD"
		};

		Question.save(question, function(data){
			var questionId = data.id;
			var optionId = data.options[0].id;
			Option.put(questionId, optionId, function(optionData){
				console.log(optionData);
			})
		})
	}]);