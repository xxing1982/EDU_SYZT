//readingCenterOtherAnswerCtrl.js

ctrls.controller("readingCenterOtherAnswerController", ['$scope', '$rootScope', '$stateParams', 'ThinkExam',
	function ($scope, $rootScope, $stateParams, ThinkExam) {
		//alert($stateParams.questionId);
		$scope.bookName = $rootScope.selectThinkExam.name;
		$scope.page = {};
		$scope.page.page = 0;
		$scope.page.size = 5;
		$scope.answer = new Array();
		getData();

		function getData(){
			ThinkExam.getOne($rootScope.id, $stateParams.questionId, $scope.page.page, $scope.page.size, function(data){
				if($scope.answer == ""){
					$scope.answer = data.content;
				}
				else{
					$scope.answer.push(data.content);
				}
			});
		}
		$scope.addCount = function(){
			$scope.page.page = $scope.page.page + 1;
			getData();
		}
	}
]);