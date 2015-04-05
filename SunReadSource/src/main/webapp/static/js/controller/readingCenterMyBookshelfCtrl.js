//readingCenterMyBookshelfCtrl.js

ctrls.controller("readingCenterMyBookshelfController", ['$scope','para',
    'Bookshelf','BookInShelf',function($scope, para, Bookshelf,BookInShelf) {
	$scope.name='阅读中心->我的书架';
        
    var pageSize = 10;
    
    var bookshelf = Bookshelf.get(function(){
        console.log(bookshelf);
    })
    $scope.shelf = bookshelf;
        
//    var bookInShelf = BookInShelf.get(function(){
//        console.log(bookInShelf);        
//    })
    
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
        
    

    //para.set("pass para");
}]);

//var booksCtrl = angular.module('nourControllers',['nourConfig', 'ngResource','bookInShelfService']);