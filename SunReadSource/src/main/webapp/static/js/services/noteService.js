var noteServices = angular.module('noteServices', ['ngResource', "nourConfig"]);

// Note object(s)
noteServices.factory('Note',['$resource', 'config',
	function($resource, config){
		return $resource("http://127.0.0.1:8080/api/notes?page=0&size=10&sortBy=id",{},{});
	}]);
