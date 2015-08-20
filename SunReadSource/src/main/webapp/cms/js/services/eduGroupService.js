var adminServices = angular.module('eduGropuServices', ['ngResource', 'nourConfig']);

adminServices.factory('EduGropu',['$resource', 'config', '$http',
	function($resource, config, $http){
		var api = {};
		api.Add = function(data, callback){
			$http.post(config.HOST + 'eduGroup/', data)
			.success(function(data, status, headers, config){
				callback(data);s
			})
		};
		api.Update = function(data, callback){
			$http.put(config.HOST + 'eduGroup/' + data.id, data)
			.success(function(data, status, headers, config){
				callback(data);
			})
		};
		api.Delete = function(data, callback){
			$http.delete(config.HOST + 'eduGroup/'+ data)
			.success(function(data, status, headers, config){
				callback(data);
			})
		};
		return api;
	}]);

adminServices.factory('GetEduGroup', ['$resource', 'config',
	function($resource, config){
		//return $resource(config.HOST + "eduGroups?page=:page&size=:size&sortBy=id",
		return $resource(config.HOST + "eduGroups/search?name=:name&page=:page&size=:size&sortBy=id",
			{name:'@_name', page:'@_page', size:'@_size'}, {}
			);
	}]);
