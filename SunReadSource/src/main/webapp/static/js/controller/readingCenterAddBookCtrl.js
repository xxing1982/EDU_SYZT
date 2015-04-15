ctrls.controller("readingCenterAddBookController", ['$scope', 'LackFeedback'
,function ($scope, LackFeedback) {
	$scope.addBook = '我要选书';
    
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
        LackFeedback.save($scope.lackBook);
    }
}]);