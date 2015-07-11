angular.module('joinShelfServices',['addbookToShelfServices']).
  factory('JoinShelf',['AddbookToShelf','$rootScope',function(AddbookToShelf,$rootScope){
    // JoinShelf.prototype.bookshelfId = $rootScope.id;
    var JoinShelf = function(){
    };

    JoinShelf.prototype.joinShelf = function(terms){
      var bookId = terms.id;
      var bookName = terms.name;
      if(typeof(bookName) ==='undefined')
        bookName = terms.bookName;
      var bookInShelf = {
          bookAttribute: false,
          readState: false
          }

      AddbookToShelf.save({bookshelfId:$rootScope.id,bookId:bookId},bookInShelf,function(data){
        $rootScope.modal ={
            title: "添加成功",
            content:"已将"+bookName+"加入书架"
          };
        $('#alert-modal').modal();
      },function(error){
        $rootScope.modal ={
          title: "添加失败",
          content:bookName+"已在书架中"
        };
        $('#alert-modal').modal();
      });
    };
    return JoinShelf;
  }]);
//
