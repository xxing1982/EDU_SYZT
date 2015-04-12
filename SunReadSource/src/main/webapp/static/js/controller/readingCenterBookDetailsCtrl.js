ctrls.controller("readingCenterBookDetailsController", ['$scope', 'para',
    'BookDetail', 'NoteTake', function($scope, para, BookDetail, NoteTake){                                                    
	$scope.name = '书籍详情';
        
    var bookDetail = BookDetail.get(function(){
        console.log(bookDetail);
        
        // Initlizate the note entity
        $scope.noteTake = new NoteTake(bookDetail);
    })
    
    $scope.bookDetails = bookDetail;

}]);