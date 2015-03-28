//readingCenterMyBookshelfCtrl.js

ctrls.controller("readingCenterMyBookshelfController", ['$scope',
    'Bookshelf',function($scope,Bookshelf) {
	$scope.name='阅读中心->我的书架';
    
    var bookshelf = Bookshelf.get(function(){
        console.log(bookshelf);
    })
    $scope.shelf = bookshelf;
}]);

var booksCtrl = angular.module('nourControllers',['nourConfig', 'ngResource','bookInShelfService');