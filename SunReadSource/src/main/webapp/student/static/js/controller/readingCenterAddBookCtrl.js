ctrls.controller("readingCenterAddBookController", ['$rootScope','$scope', 'LackFeedback'
,function ($rootScope,$scope, LackFeedback) {
	$scope.addBook = '我要选书';
	$scope.language = 1;

	function validateCtrl($scope) {
    $scope.author = 'John Doe';
    $scope.email = 'john.doe@gmail.com';
}
    $scope.lackBookFeedback = function(){
        $scope.lackBook={
          author:$scope.author,
          isbn:$scope.isbn,
          name:$scope.name,
          publisher:$scope.publisher,
          language:$scope.language,
          publicationDate:$scope.publishDate
        }
        console.log($scope.lackBook);
        var added =  LackFeedback.save($scope.lackBook,function(data){
					$rootScope.modal ={
						title: "缺书反馈",
						content:"添加成功"
					};
					$('#alert-modal').modal();
					$rootScope.modal.click = function(){
						location.reload();
					}
					console.log(data);
				},function(error){
					$rootScope.modal ={
						title: "缺书反馈",
						content:"反馈失败"+error.statusText
					};
					$('#alert-modal').modal();
					console.log(error);
					$rootScope.modal.click = function(){
						location.reload();
					}
				});
				// if(added.isbn ==="")
				// 	alert("书已存在");
    }

		$scope.isbnValid = function(){
			var validIsbn = $scope.isbn;
			if(typeof(validIsbn) ==='undefined')
				return false;
			if(validIsbn.length ===10 || validIsbn.length === 13){
				return true;
			}
			else{
				return false;
			}
		};

		$scope.isRequired = function(model){
			if(typeof(model) ==='undefined')
				return false;
			else
				return true;
		}

		$("#quickSearchTab div")[0].click();
}]);
