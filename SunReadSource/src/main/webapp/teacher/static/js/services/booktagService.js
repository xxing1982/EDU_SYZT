var booktagServices = angular.module('booktagServices', ['ngResource', "nourConfig"]);

// Booktag object(s)
booktagServices.factory('Booktag',['$resource', 'config',
	function($resource, config){
		return $resource(config.HOST + "booktags",
            {}, {}
        );
	}]);

//Get book By tag
booktagServices.factory('GetBooksByTag',['$resource', 'config',
	function($resource, config){
		return $resource(config.HOST + "books/tags?page=:page&size=:size&" +
													"lesson=:LESSON&grade=:GRADE&subject=:SUBJECT&chapter=:CHAPTER&theme=:THEME",
            {page:'@_page', size:'@_size',sortBy:'@_sortBy'}, {}
        );
	}]);
