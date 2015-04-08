ctrls.controller("readingCenterBookDetailsController", ['$scope', 'para',
    'BookDetail', 'Note', function($scope, para, BookDetail, Note){                                                    
	$scope.name = '书籍详情';
        
    var bookDetail = BookDetail.get(function(){
        console.log(bookDetail);
        
    })
    
    $scope.bookDetails = bookDetail;
    
        
    // Take note entity and methods
    $scope.note = new Object();
    $scope.note.send = function(){
        
        // Create a note entity from input 
        var note = {title: this.title, content: this.content};
        
        // POST the note by the note service with book id
        Note.save({by: "books", id: bookDetail.id}, note);
    }
}]);