var bookshelfServices = angular.module('bookshelfServices', ['ngResource', "nourConfig"]);

// User object(s)
bookshelfServices.factory('Bookshelf',['$resource', 'config',
	function($resource, config){
		return $resource("http://localhost:9999/api/bookshelf/1");
	}]);

var bookInShelfService = angular.module('bookInShelfServices', ['ngResource', "nourConfig"]);

bookInShelfService.factory('BookInShelf',['$resource', 'config',
	function($resource, config){
		return $resource(
            "http://localhost:9999/api/bookshelf/1/bookInShelf"
        ,{},{query:{
                method:"get",
                isArray:true
            }         
        }); 
    }]);