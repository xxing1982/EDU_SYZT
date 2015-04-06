var bookshelfServices = angular.module('bookshelfServices', ['ngResource', "nourConfig"]);

// User object(s)
bookshelfServices.factory('Bookshelf',['$resource', 'config',
	function($resource, config){
		return $resource("/api/bookshelf/1");
	}]);

var bookInShelfService = angular.module('bookInShelfServices', ['ngResource', "nourConfig"]);

bookInShelfService.factory('BookInShelf',['$resource', 'config',
	function($resource, config){
		return $resource(
            "/api/bookshelf/1/bookinshelf?page=:page&size=:size&sortBy=id",
            {page:'@_page', size:'@_size'},{}
        ); 
    }]);

var addbookToShelfService = angular.module('addbookToShelfServices', ['ngResource', "nourConfig"]);

addbookToShelfService.factory('AddbookToShelf',['$resource', 'config',
	function($resource, config){
		return $resource(
            "/api/bookshelf/:bookshelfId/books/:bookId/bookinshelf",
            {bookshelfId:'@_bookshelfId', bookId:'@_bookId'},{}
        ); 
    }]);