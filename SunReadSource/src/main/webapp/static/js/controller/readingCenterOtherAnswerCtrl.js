//readingCenterOtherAnswerCtrl.js

ctrls.controller("readingCenterOtherAnswerController", ['$scope', '$rootScope', '$stateParams', 'ThinkExam',
	function ($scope, $rootScope, $stateParams, ThinkExam) {
		//alert($stateParams.questionId);
		$scope.bookName = $rootScope.selectThinkExam.name;
		$scope.page = {};
		$scope.page.page = 0;
		$scope.page.size = 5;
		getData();

		function getData(){
			ThinkExam.getOne($rootScope.id, $stateParams.questionId, $scope.page.page, $scope.page.size, function(data){
				$scope.answer = data.content;
			});

		}
	}
]);