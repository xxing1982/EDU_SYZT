ctrls.controller("readingCenterBookDetailsController", ['$scope', 'para',
    'BookDetail',function($scope,para,BookDetail){                                                    
	$scope.name = '书籍详情';
        
    var bookDetail = BookDetail.get(function(){
        console.log(bookDetail);
    })
    
    $scope.bookDetails = bookDetail;
    
}]);