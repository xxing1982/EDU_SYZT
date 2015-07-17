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
<<<<<<< HEAD
		return $resource(config.HOST + "books/tags?page=:page&size=:size&sortBy=:sortBy&" +
													"lesson=:lesson&grade=:grade&subject=:subject&chapter=:chapter&theme=:theme",
=======
		return $resource(config.HOST + "books/tags?page=:page&size=:size&sortBy=:sortBy&lesson=:lesson&grade=:grade&subject=:subject&chapter=:chapter&theme=:theme",
>>>>>>> origin/master
            {page:'@_page', size:'@_size',sortBy:'@_sortBy',lesson:'@_lesson',grade:'@_grade'
						,subject:'@_subject',chapter:'@_chapter',theme:'@_theme'}, {}
        );
	}]);
