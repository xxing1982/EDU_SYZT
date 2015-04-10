//readingCenterMyEvaluatingCtrl.js

ctrls.controller("readingCenterMyEvaluatingController", ['$scope', '$rootScope', 'VerifyExam', 'WordExam', 'ThinkExam',
	function ($scope, $rootScope, VerifyExam, WordExam, ThinkExam) {
		VerifyExam.getAllInfo($rootScope.id, function(data){
			$scope.verifyExams = data.examDTOs;
		});
		//做到弹出框绑定id,是显示评价还是未评价
		WordExam.getAllInfo($rootScope.id, function(data){
			$scope.wordExams = data.examDTOs;
		});

		ThinkExam.getAllInfo($rootScope.id, function(data){
			$scope.thinkExams = data.examDTOs;
		});

		$scope.MyAnswer = function(thinkExam){
			$rootScope.selectThinkExam = thinkExam;
		}

		function hideAllTabs(){
			$scope.isCertification = false;
			$scope.isWord = false;
			$scope.isThinking = false;
		}
		$scope.showCertification = function(){
			hideAllTabs();
			$scope.isCertification = true;
		}
		$scope.showWord = function(){
			hideAllTabs();
			$scope.isWord = true;
		}
		$scope.showThinking = function(){
			hideAllTabs();
			$scope.isThinking = true;
		}
		$scope.isCertification = true;
	}]);