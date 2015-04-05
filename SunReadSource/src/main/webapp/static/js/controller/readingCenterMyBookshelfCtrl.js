//readingCenterMyBookshelfCtrl.js

ctrls.controller("readingCenterMyBookshelfController", ['$scope','para',
    'Bookshelf','BookInShelf',function($scope, para, Bookshelf,BookInShelf) {
	$scope.name='阅读中心->我的书架';
    
    var bookshelf = Bookshelf.get(function(){
        console.log(bookshelf);
    })
    $scope.shelf = bookshelf;
        
    var bookInShelf = BookInShelf.get(function(){
        console.log(bookInShelf);        
    })

    //para.set("pass para");
}]);

//var booksCtrl = angular.module('nourControllers',['nourConfig', 'ngResource','bookInShelfService']);