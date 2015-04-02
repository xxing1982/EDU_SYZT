//readingCenterMyBookshelfCtrl.js

ctrls.controller("readingCenterMyBookshelfController", ['$scope','para',
    'Bookshelf',function($scope, para, Bookshelf) {
	$scope.name='阅读中心->我的书架';
    
    var bookshelf = Bookshelf.get(function(){
        console.log(bookshelf);
    })
    $scope.shelf = bookshelf;

    //para.set("pass para");
}]);

//var booksCtrl = angular.module('nourControllers',['nourConfig', 'ngResource','bookInShelfService']);