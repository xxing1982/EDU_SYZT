//readingCenterTestSubjectiveCtrl.js

ctrls.controller("readingCenterTestSubjectiveController", ['$scope', '$rootScope', 'ThinkExam', function ($scope, $rootScope, ThinkExam) {
	if ($rootScope.exam == undefined) {
		window.location.href = '/student/protype/index.html#/readingCenter/myBookshelf';
	};
	$scope.bookName = $rootScope.exam.bookName;

	ThinkExam.get($rootScope.exam.bookId, function(data){
		ThinkExam.get($rootScope.exam.bookId, function(dataquestion){
			if (dataquestion.length < 1) {
				$rootScope.modal = {};
				$rootScope.modal.title="提示";
				$rootScope.modal.content="目前没有测试题！";
				$rootScope.modal.click = function(){
					location.reload();
				}
				$('#alert-modal').modal();
			}
			//get questions by type
			
			var map = {};
			for(var i = 0; i < data.length; i++){
				if (!(data[i].questionType in map)) {
					if(map[data[i].questionType] == undefined){
						map[data[i].questionType] = new Array();
					}
					map[data[i].questionType].push(data[i]);
				}
				else{
					map[data[i].questionType].push(data[i]);
				}
			}
			$scope.questions = new Array();
			for(value in map){
				var question = {};
				question.type = value;
				question.array = map[value];
				$scope.questions.push(question);
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
					if (data[i].content == undefined ||data[i].content.length < 20) {
						//alert("题目：" + data[i].topic + "长度少于20！");
						$rootScope.modal = {};
						$rootScope.modal.title="提示";
						$rootScope.modal.content="题目：" + data[i].topic + "长度少于20！";
						$('#alert-modal').modal();
						return;
					};
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