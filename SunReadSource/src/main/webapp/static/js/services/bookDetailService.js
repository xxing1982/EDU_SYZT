var bookDetailServices = angular.module('bookDetailServices', ['ngResource', "nourConfig"]);

bookDetailServices.factory('BookDetail',['$resource', 'config',
	function($resource, config){
		return $resource("http://localhost:9999/api/books/1");
	}]);