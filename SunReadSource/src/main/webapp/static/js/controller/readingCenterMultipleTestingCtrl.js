//readingCenterMultipleTestingCtrl.js

ctrls.controller("readingCenterMultipleTestingController", ['$rootScope', '$scope', 'VerifyExam', 'WordExam', 'ThinkExam', 
	function ($rootScope, $scope, VerifyExam, WordExam, ThinkExam) {
		if ($rootScope.exam == undefined) {
			window.location.href="/protype/index.html#/readingCenter/myBookshelf";
		};
		$scope.title = $rootScope.exam.typeName;
		$scope.bookName = $rootScope.exam.bookName;
		$scope.IsFinish = false;
		var testExam;
		$scope.current = 0;
		switch($rootScope.exam.id){
			case 0:
			testExam = VerifyExam;
			VerifyExam.get($rootScope.id, $rootScope.exam.bookId, function(data){
				Initial(data);
				$scope.questions = data;
				$scope.questions.current = $scope.questions[$scope.current];
			});
			break;
			case 1:
			testExam = WordExam;
			WordExam.get($rootScope.id, $rootScope.exam.bookId, function(data){
				Initial(data);
				$scope.questions = data;
				$scope.questions.current = $scope.questions[$scope.current];
			});
			break;
			case 2:
				testExam = ThinkExam;
				$scope.questions = ThinkExam.get($rootScope.student.level, function(data){
					console.log(data);
				});
			break;
		}		
		function Initial(data){
			$scope.myAnswer = {
				"studentId": $rootScope.id,
				"question": data
			};
			$scope.myAnswer.answers = new Array();
			$scope.total = data.length;
			$scope.nextQuestion = function(){
				$scope.isActiveA = false;
				$scope.isActiveB = false;
				$scope.isActiveC = false;
				$scope.isActiveD = false;
				if ($scope.current < $scope.questions.length) {
					$scope.current ++;
					$scope.questions.current = $scope.questions[$scope.current];
					if ($scope.current == $scope.questions.length - 1) {
						$scope.IsFinish = true;
					}
				}
			};

			$scope.FinishExam = function(){
				testExam.submitExam($scope.myAnswer, function(examData){
					console.log(examData);
				});
			}

			$scope.Select = function(dataa, option){
				var answer = {};
				answer.studentId = $rootScope.id;
				answer.option = dataa;
				answer.question = $scope.questions[$scope.current];
				$scope.myAnswer.answers[$scope.current] = answer;

				$scope.isActiveA = false;
				$scope.isActiveB = false;
				$scope.isActiveC = false;
				$scope.isActiveD = false;
				switch(option){
					case 'A':
						$scope.isActiveA = true;
						break;
					case 'B':
						$scope.isActiveB = true;
						break;
					case 'C':
						$scope.isActiveC = true;
						break;
					case 'D':
						$scope.isActiveD = true;
						break;
				}
			}
		}
		
	}]);