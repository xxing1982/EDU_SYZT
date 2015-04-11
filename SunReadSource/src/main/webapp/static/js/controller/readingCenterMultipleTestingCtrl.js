//readingCenterMultipleTestingCtrl.js

ctrls.controller("readingCenterMultipleTestingController", ['$rootScope', '$scope', 'VerifyExam', 'WordExam', 'ThinkExam', 
	function ($rootScope, $scope, VerifyExam, WordExam, ThinkExam) {
		if ($rootScope.exam == undefined) {
			window.location.href="/protype/index.html#/readingCenter/myBookshelf";
		};
		$scope.title = $rootScope.exam.typeName;
		$scope.bookName = $rootScope.exam.bookName;

		var testExam;
		switch($rootScope.exam.id){
			case 0:
				testExam = VerifyExam;
				$scope.questions = VerifyExam.get($rootScope.student.id, $rootScope.exam.bookId, function(data){
					console.log(data);
				});
				break;
			case 1:
				testExam = WordExam;
				$scope.questions = WordExam.get($rootScope.student.id, $rootScope.exam.bookId, function(data){
					console.log(data);
				});
				break;
			case 2:
				testExam = ThinkExam;
				$scope.questions = ThinkExam.get($rootScope.student.level, function(data){
					console.log(data);
				});
				break;
		}
		$scope.current = 0;
		$scope.questions.current = $scope.questions[$scope.current];


		

		$scope.nextQuestion = function(){
			if ($scope.current < $scope.questions.length()) {
				$scope.current ++;
			};
		};
		
	}]);