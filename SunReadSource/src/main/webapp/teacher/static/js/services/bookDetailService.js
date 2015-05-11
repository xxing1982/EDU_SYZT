var bookDetailServices = angular.module('bookDetailServices', ['ngResource', "nourConfig"]);

bookDetailServices.factory('BookDetail',['$resource', 'config',
	function($resource, config){
        return $resource(config.HOST + "books/:id",
            {id:'@id'}, {}
        );
	}]);

bookDetailServices.factory('Review',['$resource', 'config',
	function($resource, config){
		return $resource(config.HOST + "reviews/:id",
            {id:'@id'}, {}
        );
	}]);