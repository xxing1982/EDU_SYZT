//readingCenterMultipleTestingCtrl.js

ctrls.controller("readingCenterMultipleTestingController", ['$rootScope', '$scope', 'VerifyExam', 'WordExam', 'ThinkExam',
	function ($rootScope, $scope, VerifyExam, WordExam, ThinkExam) {
		if ($rootScope.exam == undefined) {
			window.location.href = '/protype/index.html#/readingCenter/myBookshelf';
		};
		$scope.title = $rootScope.exam.typeName;
		$scope.bookName = $rootScope.exam.bookName;
		$scope.IsFinish = false;
		$scope.IsCilck = false;
		var testExam;
		$scope.current = 0;
		switch($rootScope.exam.id){
			case 0:
			testExam = VerifyExam;
			VerifyExam.get($rootScope.id, $rootScope.exam.bookId, function(data){
				Initial(data);
				$scope.myAnswer.examType = 'VERIFY';
				$scope.questions = data.questions;
				$scope.questions.current = $scope.questions[$scope.current];
			});
			break;
			case 1:
			testExam = WordExam;
			WordExam.get($rootScope.id, $rootScope.exam.bookId, function(data){
				Initial(data);
				$scope.myAnswer.examType = 'WORD';
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
                "bookId":  $rootScope.exam.bookId,
				"studentId": $rootScope.id,
				"questions": data
			};
			$scope.myAnswer.answers = new Array();
			$scope.total = data.length;
			$scope.nextQuestion = function(){
				if (!$scope.isActiveA && !$scope.isActiveB && !$scope.isActiveC && !$scope.isActiveD) {
					alert("请选择一项！");
					return;
				};
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
				$scope.IsCilck = true;
				testExam.submitExam($scope.myAnswer, function(examData){
						var score=0;
						if ($rootScope.exam.id == 0) {
							score = examData.exam.examScore;
						}
						else{
							score = examData.examScore;
						}
						//examData.exam.examScore;
						if($rootScope.exam.id == 1){
							$rootScope.exam.score = score;
							$rootScope.exam.isVerify = false;
							window.location.href="/protype/index.html#/readingCenter/success";
						}
						else if (examData.exam.pass) {
							$rootScope.exam.score = score;
							$rootScope.exam.isVerify = true;
							window.location.href="/protype/index.html#/readingCenter/success";
						}
						else{
							window.location.href="/protype/index.html#/readingCenter/failed";
						}
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
