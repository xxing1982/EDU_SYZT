var userServices = angular.module('userServices', ['ngResource', "nourConfig"]);

// User object(s)
userServices.factory('User',['$resource', 'config',
	function($resource, config){
		return $resource("http://movieapp-sitepointdemos.rhcloud.com/api/movies/:id");
	}]);