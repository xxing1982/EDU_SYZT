ctrls.controller("readingCenterBookDetailsController", ['$scope', 'para',
    'BookDetail', 'Note', function($scope, para, BookDetail, Note){                                                    
	$scope.name = '书籍详情';
        
    var bookDetail = BookDetail.get(function(){
        console.log(bookDetail);
        
        // Initlizate the note entity
        $scope.takeNote = new takeNote(Note, bookDetail);
    })
    
    $scope.bookDetails = bookDetail;

}]);