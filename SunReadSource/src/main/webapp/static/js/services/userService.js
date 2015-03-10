var userServices = angular.module('userServices', ['ngResource', "nourConfig"]);

// User object(s)
userServices.factory('User',['$resource', 'config',
	function($resource, config){
		return $resource("http://movieapp-sitepointdemos.rhcloud.com/api/movies/:id");
	}]);
/*
userServices.factory('GetUserList',['$http',
	function($http,){
		$http.get('http://movieapp-sitepointdemos.rhcloud.com/api/movies/').success(function(data){
			
		});
	}]);
*/
userServices.factory('GetUserList',['$http',
	function($http){
		var api = {};
		api.getUsers = function(callback){
			$http.get('http://movieapp-sitepointdemos.rhcloud.com/api/movies/').success(function(data){
				api.data = data;
				callback();
			});
		}
		return api;
	}]);