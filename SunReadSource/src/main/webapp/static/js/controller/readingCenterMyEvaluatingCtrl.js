//readingCenterMyEvaluatingCtrl.js

ctrls.controller("readingCenterMyEvaluatingController", ['$scope', '$rootScope', 'VerifyExam', 'WordExam', 'ThinkExam', 'OneBookInShelf', 'AddReview',
	function ($scope, $rootScope, VerifyExam, WordExam, ThinkExam, OneBookInShelf, AddReview) {
		$scope.content = "";
		$scope.title = "";
		$scope.source = {};
		$scope.count = 3;
		//source
		VerifyExam.getAllInfo($rootScope.id, function(data){
			$scope.source.verifyExams = data.examDTOs;
			for(var i = 0; i < $scope.source.verifyExams.length; i++){
				var index = i;
				OneBookInShelf.get({id:$rootScope.id,bookId:data.examDTOs[index].bookId},function(dataDetail){
					//TRUE 已认证
					$scope.source.verifyExams[index].verify = dataDetail.readState;
				});
			}
			$scope.verifyExams = $scope.source.verifyExams.slice(0, $scope.count);
		});

		WordExam.getAllInfo($rootScope.id, function(data){
			$scope.source.wordExams = data.examDTOs;
			for(var i = 0; i < $scope.source.wordExams.length; i++){
				var index = i;
				OneBookInShelf.get({id:$rootScope.id,bookId:data.examDTOs[index].bookId},function(dataDetail){
					$scope.source.wordExams[index].verify = dataDetail.readState;
				});
			}
			$scope.wordExams = $scope.source.wordExams.slice(0, $scope.count);
		});

		ThinkExam.getAllInfo($rootScope.id, function(data){
			$scope.source.thinkExams = data.examDTOs;
			for(var i = 0; i < $scope.source.thinkExams.length; i++){
				var index = i;
				OneBookInShelf.get({id:$rootScope.id,bookId:data.examDTOs[index].bookId},function(dataDetail){
					//TRUE 已认证
					$scope.source.thinkExams[index].verify = dataDetail.readState;
				});
			}
			$scope.thinkExams = $scope.source.thinkExams.slice(0, $scope.count);
		});

		$scope.addCount = function(){
			$scope.count = $scope.count + 3;
			$scope.verifyExams = $scope.source.verifyExams.slice(0, $scope.count);
			$scope.wordExams = $scope.source.wordExams.slice(0, $scope.count);
			$scope.thinkExams = $scope.source.thinkExams.slice(0, $scope.count);
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

		$scope.evaluate = function(){
           var review = {};
           review.bookId = $scope.chooseBookId;
           review.title = $scope.title;
           review.content = $scope.content;
           review.rate = 5;
           AddReview.AddReview(review.bookId, review, function(data){
           		console.log(data);
           })
       }
	}]);