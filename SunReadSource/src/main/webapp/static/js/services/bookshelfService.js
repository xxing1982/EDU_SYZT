var bookshelfServices = angular.module('bookshelfServices', ['ngResource', "nourConfig"]);

// User object(s)
bookshelfServices.factory('bookshelf',['$resource', 'config',
	function($resource, config){
		return $resource("http://movieapp-sitepointdemos.rhcloud.com/api/movies/:id");
	}]);