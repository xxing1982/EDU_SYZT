/* 
    Bookshelf view object 
*/
var BookshelfView = function () {
    
    // Some states of the closure
    this.Bookshelfs = {page: 0,
                  size: this.bookshelfPageSize, 
                  finished: false, 
                  content: []};
};


/*
    Some constants 
*/
BookshelfView.prototype.bookshelfPageSize = 6;
BookshelfView.prototype.stateTexts = { more : "加载更多", loading: "更多加载中...", nomore: "没有了"};

/*
    Invoke this method to load more bookshelfs
*/
BookshelfView.prototype.ShowMoreBooksInShelf = function (arguments){
        
    // Change the loading state to loading
    this.loadingState = this.stateTexts.loading;
    
    // Make the reference to the Bookshelfs object
    var Bookshelfs = this.Bookshelfs;

    // Make the reference to the BookshelfView object
    var BookshelfView = this;

    // GET the Bookshelfs entity
    var newPage = this.BookInShelf.get(
        $.extend({}, { page: Bookshelfs.page, size: Bookshelfs.size, direction: "DESC", sortBy: "id"}, arguments), function(){                        
            /*
                Check the lastPage flag and set stateTexts
            */
            
            if (newPage.lastPage){
                
                // Get the last page of the Bookshelfs, 
                // Change the state of the loading state and turn on finished
                Bookshelfs.loadingState = BookshelfView.stateTexts.nomore;
                if (!Bookshelfs.finished) {
                    Bookshelfs.content = Bookshelfs.content.concat(newPage.content);
                }
                Bookshelfs.finished = true;
            } else {
                Bookshelfs.finished = false;
                Bookshelfs.loadingState = BookshelfView.stateTexts.more;
                Bookshelfs.content = Bookshelfs.content.concat(newPage.content);
                Bookshelfs.page++;
            }
        });
};

///*
//    Invoke this method to load booksInShelf for the first time
//*/
//BookshelfView.prototype.ShowBooksInShelf = function(bookshelf){
//    
//    // Toggle booksInShelf
//    bookshelf.showBooksInShelf = !bookshelf.showBooksInShelf;
//    
//    // Turning on the booksInShelf 
//    if (bookshelf.showBooksInShelf) {
//        // Initlizate the bookshelf entity
//        bookshelf.last = 0;
//        bookshelf.page = 0;
//        bookshelf.size = this.bookinshelfPageSize;
//        bookshelf.finished = false;
//        bookshelf.BooksInShelf = {content: []};
//
//        // Get the content of the booksInShelf
//        this.ShowMoreBooksInShelf.call(this, bookshelf);
//    }
//}
//
//
///*
//    Invoke this method to load more booksInShelf
//*/
//BookshelfView.prototype.ShowMoreBooksInShelf = function (bookshelf){
//    
//    // Change the loading state to loading
//    bookshelf.loadingState = this.stateTexts.loading;
//
//    // Make the reference to the BookshelfView object
//    var BookshelfView = this;
//
//    // GET the BooksInShelf entity
//    var newPage = this.BookInShelf.get({by: "bookshelfs", id: bookshelf.id, page: bookshelf.page, size: bookshelf.size, sortBy: "id", direction: "DESC"}, function(){               
//        if (newPage.lastPage){
//            
//            // Get the last page of the BooksInShelf, 
//            // Change the state of the loading state and turn on finished
//            bookshelf.loadingState = BookshelfView.stateTexts.nomore;
//            if (!bookshelf.finished) {
//                bookshelf.BooksInShelf.content = bookshelf.BooksInShelf.content.concat(newPage.content);
//            }
//            bookshelf.finished = true;
//        } else {
//            bookshelf.finished = false;
//            bookshelf.loadingState = BookshelfView.stateTexts.more;
//            bookshelf.BooksInShelf.content = bookshelf.BooksInShelf.content.concat(newPage.content);
//            bookshelf.page ++;
//        }
//    });
//};


/*
    Create the module for bookshelfViewServices
*/
angular.module('bookshelfViewServices', ['bookshelfServices','bookInShelfServices']).
    factory('BookshelfView', ['Bookshelf','BookInShelf',function (Bookshelf,BookInShelf) {
        
        // Dependency injection
        BookshelfView.prototype.Bookshelf = Bookshelf;
        BookshelfView.prototype.BookInShelf = BookInShelf;
    
        // Return the BookshelfView object with
        return BookshelfView;
    }]);