var questionServices = angular.module('questionServices', ['ngResource', 'nourConfig']);

questionServices.factory('Objectivequestions',['$resource', 'config', '$http',
	function($resource, config, $http){
		var api = {};
		api.Update = function(data, callback){
			$http.put(config.HOST + 'objectivequestion/', data)
			.success(function(data, status, headers, config){
				callback(data);
			})
		};
		return api;
	}]);

questionServices.factory('GetObjectivequestions', ['$resource', 'config',
	function($resource, config){
		return $resource(config.HOST + "objectivequestions?page=:page&size=:size&sortBy=id",
			{page:'@_page', size:'@_size'}, {}
			);
	}]);

questionServices.factory('Option',['$resource', 'config', '$http',
	function($resource, config, $http){
		var api = {};
		api.Update = function(data, callback){
			$http.put(config.HOST + 'option/', data)
			.success(function(data, status, headers, config){
				callback(data);
			})
		};
		return api;
	}]);

questionServices.factory('GetSubjectivequestions', ['$resource', 'config',
	function($resource, config){
		return $resource(config.HOST + "subjectivequestions?page=:page&size=:size&sortBy=id",
			{page:'@_page', size:'@_size'}, {}
			);
	}]);
questionServices.factory('Subjectivequestions',['$resource', 'config', '$http',
	function($resource, config, $http){
		var api = {};
		api.Update = function(data, callback){
			$http.put(config.HOST + 'subjectivequestion/', data)
			.success(function(data, status, headers, config){
				callback(data);
			})
		};
		return api;
	}]);
