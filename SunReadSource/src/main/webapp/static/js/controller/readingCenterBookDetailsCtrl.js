ctrls.controller("readingCenterBookDetailsController", ['$scope', '$rootScope', 'para',
    'BookDetail', 'NoteTake', function($scope, $rootScope, para, BookDetail, NoteTake){                                                    
	$scope.name = '书籍详情';
        
    var bookDetail = BookDetail.get(function(){
        console.log(bookDetail);
        
        // Initlizate the note entity
        $scope.noteTake = new NoteTake(bookDetail);
    })
    
    $scope.bookDetails = bookDetail;

    $scope.SubjectiveTest = function(data){
        $rootScope.exam.id = 1;
        $rootScope.exam.bookId = $scope.bookDetail.name;
        $rootScope.exam.bookName = $scope.bookDetail.name;
        $rootScope.exam.typeName = "我的书架 > 思维训练";
    }
    $scope.WordTest = function(data){
        $rootScope.exam.id = 2;
        $rootScope.exam.bookId = $scope.bookDetail.name;
        $rootScope.exam.bookName = $scope.bookDetail.name;
        $rootScope.exam.typeName = "我的书架 > 词汇训练";
    }

}]);