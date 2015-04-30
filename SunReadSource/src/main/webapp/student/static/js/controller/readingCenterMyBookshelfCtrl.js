``//readingCenterMyBookshelfCtrl.js

ctrls.controller("readingCenterMyBookshelfController", ['$rootScope', '$scope','BookshelfView',
    'Bookshelf','BookInShelf','DropBookFromShelf', 'NoteTake', 'BookDetail', 'Dropzone', 'config', function($rootScope, $scope,BookshelfView,Bookshelf,BookInShelf,DropBookFromShelf,NoteTake, BookDetail, Dropzone, config) {
	$scope.name='阅读中心->我的书架';

    var size = 9;
    var stateTexts = { more : "加载更多",loading: "更多加载中...",nomore: "没有了"};
    $scope.unreadLoading = stateTexts.loading;
    $scope.readLoading = stateTexts.loading;
    // Get the user id form rootScope
    $scope.arguments = {id: $rootScope.id};

    // $scope.BookshelfView = new BookshelfView();
    // $scope.BookshelfView.ShowMoreBooksInShelf($scope.arguments);

    $scope.statuses = [{
        id: 0,
        name:"全部",
        callback: function(){$scope.selectBookAttributes()}
    }, {
        id: 1,
        name: "必读",
        callback: function(){$scope.selectBookAttributes()}
    }, {
        id: 2,
        name: "选读",
        callback: function(){$scope.selectBookAttributes()}
    }];
    $scope.selected_status = 0;

    // //Get books with number of size.
    // var bookInShelf = BookInShelf.get({id:$rootScope.id,page:0,size:size},function(){
    //     console.log(bookInShelf);
    //     //init $scope.readBooks  & $scope.unreadBooks
    //     spileBooksByReadState(bookInShelf.content,$scope.readBooks,$scope.unreadBooks);
    //
    // });
    var unreadBooksALL;
    var readBooksALL;

    $scope.selectBookAttributes = function(){
          unreadBooksALL = new Array();
          readBooksALL = new Array();
        // console.log($scope.selected_status);
        // $scope.bookInShelf = BookInShelf.get({id:$rootScope.id,page:0,size:size},function(){
        var bookshelf = Bookshelf.get({id:$rootScope.id},function(){
          // console.log(bookshelf.booksInShelf);
          $scope.shelf = bookshelf;
          var content = bookshelf.booksInShelf;
          for(var i = 0; i < content.length; i++){
              //console.log(content[i]);
              if(content[i].readState){
                  if($scope.selected_status=== 1&& !content[i].bookAttribute)
                      continue;
                  if($scope.selected_status=== 2&& content[i].bookAttribute)
                      continue;
                   readBooksALL.push(content[i]);
              }
              else{
                  if($scope.selected_status=== 1&& !content[i].bookAttribute)
                      continue;
                  if($scope.selected_status=== 2&& content[i].bookAttribute)
                      continue;
                   unreadBooksALL.push(content[i]);
              }
          }
          $scope.unreadLoading = setLoadingState(unreadBooksALL,size);
          $scope.readLoading = setLoadingState(readBooksALL,size);

          $scope.unreadBooks = new Array();
          $scope.readBooks = new Array();

          initBooks($scope.unreadBooks,unreadBooksALL,size);
          initBooks($scope.readBooks,readBooksALL,size);
        });
    };

    $scope.unreadBooksLoadingMore = function(){
      if($scope.unreadLoading === stateTexts.nomore)
        return;
      if(loadingMore($scope.unreadBooks,unreadBooksALL,size))
        $scope.unreadLoading = stateTexts.more;
      else{
        $scope.unreadLoading = stateTexts.nomore;
      }
    }

    $scope.readBooksLoadingMore = function(){
      if($scope.readLoading === stateTexts.nomore)
        return;
      if(loadingMore($scope.readBooks,readBooksALL,size))
        $scope.readLoading = stateTexts.more;
      else{
        $scope.readLoading = stateTexts.nomore;
      }
    }

    var initBooks = function(books,booksALL,size){
      if(typeof(booksALL)=== 'undefined')
        return 0;
      for(var i=0; i<booksALL.length&&i<size;i++)
        books.push(booksALL[i]);
      // console.log(books);
      return books.length;
   };

    var setLoadingState = function(books,size){
      if(books.length<=size)
        return stateTexts.nomore;
      else if(books.length>size)
        return stateTexts.more;
    };

/*
  For loding more
  return false means nomore, then set loadingState to nomore.
  return true means more. And you can click again,
  ONE click loads nine books at most
*/
    var loadingMore = function(bookArray,bookALL,pageSize){
      var allNum = bookALL.length;
        for(var i=0;i<pageSize;i++){
          if(bookArray.length<allNum)
            bookArray.push(bookALL[bookArray.length]);
          else
            return false;
          }
      return true;
  };
//End loadingMore

//DELETE A BOOK IN SHELF
    $scope.dropBookFromShelf = function(book){
        // console.log(book.id);
        $scope.dropBook = DropBookFromShelf.remove({id:book.id},function(){
          if(typeof($scope.dropBook) === "undefined"){
              alert("删除失败");
          }
          else{
              alert("移除成功");
              $scope.selectBookAttributes();
          }
        });

    };

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


    // Image uploader
    $scope.dropzone = Dropzone(config.NOTEPIC, function(url){
        $scope.noteTake.image = url;
    });

    $scope.takeNoteByBookinshelf = function(bookinshelf){
        var bookDetail = BookDetail.get({ id: bookinshelf.bookId }, function(){
            // console.log(bookDetail);

            // Initlizate the note entity
            $scope.noteTake = new NoteTake(bookDetail);



            // Get the image server
            $scope.imageServer = config.IMAGESERVER;
        });
    }
    $scope.imageServer = config.IMAGESERVER;
}]);

var spileBooksByReadState = function(content,readBooks,unreadBooks){
  if(typeof(content)=== 'undefined')
    return;
  for(var i = 0; i < content.length; i++){
      //console.log(content[i]);
      if(content[i].readState)
          readBooks.push(content[i]);
      else
          unreadBooks.push(content[i]);
  }
}

//Must or select of books
var findBooksByBookAttibutes = function(content,books){
  if(typeof(content)=== 'undefined')
    return false;
  if(content.length==0)
      return false;
  for(var i = 0; i <  content.length; i++){
    if($scope.selected_status=== 1&& !content[i].bookAttribute)
        continue;
    if($scope.selected_status=== 2&& content[i].bookAttribute)
        continue;
      books.push(content[i]);
  }
  return true;
};

var spileBooks = function(content){
  $scope.readBooks = new Array();
  $scope.unreadBooks = new Array();
  for(var i = 0; i < content.length; i++){
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
};
var findUnreadBooks = function(content,unreadBooks){
  for(var i = 0; i < content.length; i++){
    if(!content[i].readState){
        if($scope.selected_status=== 1&& !content[i].bookAttribute)
            continue;
        if($scope.selected_status=== 2&& content[i].bookAttribute)
            continue;
        unreadBooks.push(content[i]);
    }
  }
};

//var booksCtrl = angular.module('nourControllers',['nourConfig', 'ngResource','bookInShelfService']);
