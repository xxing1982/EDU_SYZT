ctrls.controller("readingCenterAddBookController", ['$scope', 'LackFeedback'
,function ($scope, LackFeedback) {
	$scope.addBook = '我要选书';
    var language;
    var author;
    var name;
    var publisher;
    var publishDate;
    var isbn;
	
    
    $scope.lackBookFeedback = function(){
        $scope.lackBook={
          author:author,
          isbn:isbn,
          name:name,
          publisher:publisher,
          language:language,
          publicationDate:publishDate
        }
        LackFeedback.save($scope.lackBook);
    }
}]);