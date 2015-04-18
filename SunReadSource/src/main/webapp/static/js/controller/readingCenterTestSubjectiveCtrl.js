//readingCenterTestSubjectiveCtrl.js

ctrls.controller("readingCenterTestSubjectiveController", ['$scope', '$rootScope', 'ThinkExam', function ($scope, $rootScope, ThinkExam) {
	if ($rootScope.exam == undefined) {
		window.location.href=$rootScope.exam.returnURL;
	};
	$scope.bookName = $rootScope.exam.bookName;

	ThinkExam.get($rootScope.exam.bookId, function(data){
		ThinkExam.get($rootScope.exam.bookId, function(dataquestion){
			//get questions by type
			$scope.questions = new Array();
			var question = {};
			for(var i = 0; i < data.length; i++){
				if (i == 0) {
					question.array = new Array();
					question.type = data[i].questionType;
					question.array.push(data[i]);
				}
				else if(i == data.length - 1){
					question.array.push(data[i]);
					$scope.questions.push(question);
				}
				else if(question.type == data[i].questionType){
					question.array.push(data[i]);
				}
				else{
					$scope.questions.push(question);
					question = {};
					question.array = new Array();
					question.type = data[i].questionType;
					question.array.push(data[i]);
				}
			}

			$scope.finishExam = function(){
				$scope.myAnswer = {
					"bookId": $rootScope.exam.bookId,
					"studentId": $rootScope.id,
					"examType": "THINK"
				};
				$scope.myAnswer.answers = new Array();
				$scope.myAnswer.questions = dataquestion;
				for (var i = 0; i < data.length; i++) {
					answer = {};
					answer.question = dataquestion[i];
					answer.studentId = $rootScope.id;
					answer.content = data[i].content;

					$scope.myAnswer.answers[i] = answer;
				};

				ThinkExam.submitExam($scope.myAnswer, function(examData){
					window.location.href=$rootScope.exam.returnURL;
				})
			}
		})
	})
	}]);