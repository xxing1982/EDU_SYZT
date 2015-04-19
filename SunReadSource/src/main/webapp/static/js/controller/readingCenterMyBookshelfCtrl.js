//readingCenterMyBookshelfCtrl.js

ctrls.controller("readingCenterMyBookshelfController", ['$rootScope', '$scope','BookshelfView',
    'Bookshelf','BookInShelf','DropBookFromShelf',function($rootScope, $scope,BookshelfView,Bookshelf,BookInShelf,DropBookFromShelf) {
	$scope.name='阅读中心->我的书架';
        
        
    // Get the user id form rootScope
    $scope.arguments = {id: $rootScope.id};
    
    // Initlizate the noteView entity
    $scope.BookshelfView = new BookshelfView();
    $scope.BookshelfView.ShowMoreBooksInShelf($scope.arguments);
    
    console.log($scope.BookshelfView);    

    var page = 1;
    var size = 9;
    
    $scope.statuses = [{
        id: 0,
        name:"全部"
    }, {
        id: 1,
        name: "必读"        
    }, {
        id: 2,
        name: "选读"        
    }];        
    $scope.selected_status = 0; 
        
    function createLoadable(BookInShelf,page,size){
        
    }
    
    var bookshelf = Bookshelf.get({id:$rootScope.id},function(){
        console.log(bookshelf);
        });
        
    $scope.reBookshelf  = function(){  
        bookshelf = Bookshelf.get({id:$rootScope.id},function(){
        console.log(bookshelf);
        });
    };
        
    $scope.shelf = bookshelf;
    
    $scope.bookInShelf = BookInShelf.get({id:$rootScope.id,page:0,size:size},function(){
        console.log($scope.bookInShelf);
        var content = $scope.bookInShelf.content;
        $scope.readBooks = new Array();
        $scope.unreadBooks = new Array();
        for(var i = 0; i < content.length; i++){
            var j=0, k=0;
            //console.log(content[i]);
            if(content[i].readState){
                $scope.readBooks.push(content[i]);
            }
            else{
                $scope.unreadBooks.push(content[i]);
            }
        }

    });
        
    $scope.selectBookAttributes = function(){
        
        console.log($scope.selected_status);
        $scope.bookInShelf = BookInShelf.get({id:$rootScope.id,page:0,size:size},function(){
        console.log($scope.bookInShelf);
        var content = $scope.bookInShelf.content;
        $scope.readBooks = new Array();
        $scope.unreadBooks = new Array();
        for(var i = 0; i < content.length; i++){
            var j=0, k=0;
            //console.log(content[i]);
            if(content[i].readState){
                if($scope.selected_status=== 1&& !content[i].bookAttribute)
                    continue;
                if($scope.selected_status=== 2&& content[i].bookAttribute)
                    continue;
                $scope.readBooks.push(content[i]);
            }
            else{
                if($scope.selected_status=== 1&& !content[i].bookAttribute)
                    continue;
                if($scope.selected_status=== 2&& content[i].bookAttribute)
                    continue;
                $scope.unreadBooks.push(content[i]);
            }
        }

    });
    };

    
    $scope.dropBookFromShelf = function(book){
        console.log(book.id);
        $scope.dropBook = DropBookFromShelf.remove({id:book.id});
        if($scope.dropBook === null){
            alert("删除失败");
        }
        else{
            alert("移除成功");
            
            location.reload();
            
        }
            
    };
    
    console.log($scope.unreadBooks);
    $rootScope.exam = {};
    $scope.CertificationTest = function(data){
        $rootScope.exam.id = 0;
        $rootScope.exam.returnURL = "/protype/index.html#/readingCenter/myBookshelf";
        $rootScope.exam.bookId = data.bookId;
        $rootScope.exam.bookName = data.bookName;
        $rootScope.exam.typeName = "我的书架 > 认证训练";
    }
    $scope.SubjectiveTest = function(data){
        $rootScope.exam.id = 2;
        $rootScope.exam.returnURL = "/protype/index.html#/readingCenter/myBookshelf";
        $rootScope.exam.bookId = data.bookId;
        $rootScope.exam.bookName = data.bookName;
        $rootScope.exam.typeName = "我的书架 > 思维训练";
    }
    $scope.WordTest = function(data){
        $rootScope.exam.id = 1;
        $rootScope.exam.returnURL = "/protype/index.html#/readingCenter/myBookshelf";
        $rootScope.exam.bookId = data.bookId;
        $rootScope.exam.bookName = data.bookName;
        $rootScope.exam.typeName = "我的书架 > 词汇训练";
    }
}]);



//var booksCtrl = angular.module('nourControllers',['nourConfig', 'ngResource','bookInShelfService']);