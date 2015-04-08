var bookDetailServices = angular.module('bookDetailServices', ['ngResource', "nourConfig"]);

bookDetailServices.factory('BookDetail',['$resource', 'config',
	function($resource, config){
		return $resource(config.HOST + "books/1");
	}]);