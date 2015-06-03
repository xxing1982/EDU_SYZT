var fishServices = angular.module('fishServices', ['ngResource', "nourConfig"]);

fishServices.factory('Fish',['$resource', 'config', '$http',
	function($resource, config, $http){
		var api = {};
		api.getFishes = function(callback){
			$http.get(config.HOST + 'fishes')
			.success(function(data, status, headers, config){
				callback(data);
			});
		};
		return api;
	}]);