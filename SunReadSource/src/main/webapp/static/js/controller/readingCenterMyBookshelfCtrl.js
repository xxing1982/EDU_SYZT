//readingCenterMyBookshelfCtrl.js

ctrls.controller("readingCenterMyBookshelfController", ['$rootScope', '$scope','para',
    'Bookshelf','BookInShelf',function($rootScope, $scope, para, Bookshelf,BookInShelf) {
	$scope.name='阅读中心->我的书架';
        
    var pageSize = 10;
    
    var bookshelf = Bookshelf.get(function(){
        console.log(bookshelf);
    })
    $scope.shelf = bookshelf;
    
    $scope.bookInShelf = BookInShelf.get({page:0,size:pageSize},function(){
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
    
    $rootScope.exam = {};
    $scope.CertificationTest = function(data){
        $rootScope.exam.id = 0;
        $rootScope.exam.bookId = data.bookId;
        $rootScope.exam.bookName = data.bookName;
        $rootScope.exam.typeName = "我的书架 > 认证训练";
    }
    $scope.SubjectiveTest = function(data){
        $rootScope.exam.id = 1;
        $rootScope.exam.bookId = data.bookId;
        $rootScope.exam.bookName = data.bookName;
        $rootScope.exam.typeName = "我的书架 > 思维训练";
    }
    $scope.WordTest = function(data){
        $rootScope.exam.id = 2;
        $rootScope.exam.bookId = data.bookId;
        $rootScope.exam.bookName = data.bookName;
        $rootScope.exam.typeName = "我的书架 > 词汇训练";
    }
}]);

//var booksCtrl = angular.module('nourControllers',['nourConfig', 'ngResource','bookInShelfService']);