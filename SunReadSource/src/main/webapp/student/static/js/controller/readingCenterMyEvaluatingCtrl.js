//readingCenterMyEvaluatingCtrl.js

ctrls.controller("readingCenterMyEvaluatingController", ['$scope', '$rootScope', 'VerifyExam', 'WordExam', 'ThinkExam', 'AddReview', 'config',
	function ($scope, $rootScope, VerifyExam, WordExam, ThinkExam, AddReview, config) {
		$scope.content = "";
		$scope.title = "";
		$scope.rate = 0;
		$scope.imageServer = config.IMAGESERVER;
		$scope.source = {};
		$scope.countVerifyExam = 6;
		$scope.countWordExam = 6;
		$scope.countThinkExam = 6;
		
		//loading years
		

		var stateTexts = { more : "加载更多",loading: "更多加载中...",nomore: "没有了"};
		$scope.status = {
			current : stateTexts.loading,
			verify : stateTexts.loading,
			word : stateTexts.loading,
			think : stateTexts.loading
		};
		$scope.status.current = $scope.status.verify;
		//source
		VerifyExam.getAllInfo($rootScope.id, function(data){
			$scope.source.verifyExamsSource = data.examDTOs;
			$scope.source.verifyExams = data.examDTOs;
			$scope.verifyExams = $scope.source.verifyExams.slice(0, $scope.countVerifyExam);
			$scope.status.verify = stateTexts.more;
			$scope.status.current = $scope.status.verify;
		});

		WordExam.getAllInfo($rootScope.id, function(data){
			$scope.source.wordExamsSource = data.examDTOs;
			$scope.source.wordExams = data.examDTOs;
			$scope.wordExams = $scope.source.wordExams.slice(0, $scope.countWordExam);
			$scope.status.word = stateTexts.more;
			$scope.status.current = $scope.status.word;
		});

		ThinkExam.getAllInfo($rootScope.id, function(data){
			$scope.source.thinkExamsSource = data.examDTOs;
			$scope.source.thinkExams = data.examDTOs;//pictureUrl
			$scope.thinkExams = $scope.source.thinkExams.slice(0, $scope.countThinkExam);
			$scope.status.think = stateTexts.more;
			$scope.status.current = $scope.status.think;
		});

		$scope.addCount = function(){
			if ($scope.isCertification) {
				if ($scope.countVerifyExam >= $scope.source.verifyExams.length) {
					$scope.status.verify = stateTexts.nomore;
					$scope.status.current = $scope.status.verify;
				}
				$scope.countVerifyExam = $scope.countVerifyExam+ 3;
			}
			else if($scope.isWord){
				if ($scope.countWordExam >= $scope.source.wordExams.length) {
					$scope.status.word = stateTexts.nomore;
					$scope.status.current = $scope.status.word;
				}
				$scope.countWordExam = $scope.countWordExam + 3;
			}
			else if($scope.isThinking){
				if ($scope.countThinkExam >= $scope.source.thinkExams.length) {
					$scope.status.think = stateTexts.nomore;
					$scope.status.current = $scope.status.think;
				}
				$scope.countThinkExam = $scope.countThinkExam + 3;
			}
			$scope.verifyExams = $scope.source.verifyExams.slice(0, $scope.countVerifyExam);
			$scope.wordExams = $scope.source.wordExams.slice(0, $scope.countWordExam);
			$scope.thinkExams = $scope.source.thinkExams.slice(0, $scope.countThinkExam);
		}

		$scope.MyAnswer = function(thinkExam){
			$rootScope.selectThinkExam = thinkExam;
		}

		$scope.chooseBook = function(bookId){
			$scope.chooseBookId = bookId;
		}

		function hideAllTabs(){
			$scope.isCertification = false;
			$scope.isWord = false;
			$scope.isThinking = false;
		}
		$scope.showCertification = function(){
			hideAllTabs();
			$scope.isCertification = true;
			$scope.status.current = $scope.status.verify;
		}
		$scope.showWord = function(){
			hideAllTabs();
			$scope.isWord = true;
			$scope.status.current = $scope.status.word;
		}
		$scope.showThinking = function(){
			hideAllTabs();
			$scope.isThinking = true;
			$scope.status.current = $scope.status.think;
		}
		$scope.isCertification = true;

		$scope.evaluate = function(){
			var review = {};
			review.studentId = $rootScope.id;
			review.title = $scope.title;
			review.content = $scope.content;
			review.rate = $scope.rate;
			AddReview.AddReview($scope.chooseBookId, review, function(data){
				 location.reload();
			})
		}
	}]);

ctrls.filter('formatSize5', function(){
  return function(input){
  	if (input > 5)
    	return input.substring(0, 5) + '...';
    else
    	return input;
  }
});