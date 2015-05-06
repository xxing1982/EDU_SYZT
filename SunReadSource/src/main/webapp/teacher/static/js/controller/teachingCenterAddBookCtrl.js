ctrls.controller("readingCenterAddBookController", ['$scope', 'LackFeedback'
,function ($scope, LackFeedback) {
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
        var added =  LackFeedback.save($scope.lackBook);
				if(added.isbn ==="")
					alert("书已存在");
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
}]);
