var studentServices = angular.module('studentServices', ['ngResource', 'nourConfig']);

studentServices.factory('Students',['$resource', 'config', '$http',
	function($resource, config, $http){
		var api = {};
		api.Update = function(data, callback){
			$http.put(config.HOST + 'eduGroup/' + data.id, data)
			.success(function(data, status, headers, config){
				callback(data);
			})
		};
		api.Add = function(data, callback){
			$http.post(config.HOST + 'students/', data)
			.success(function(data, status, headers, config){
				callback(data);
			})
		};
		api.Delete = function(data, callback){
			$http.delete(config.HOST + 'students/'+ data)
			.success(function(data, status, headers, config){
				callback(data);
			})
			.error(function(data,header,config,status){
				callback(data);
			});
		};
		return api;
	}]);

studentServices.factory('GetStudent',['$resource', 'config', '$http',
	function($resource, config){
		return $resource(config.HOST + "campus/:campusid/hotreaders?page=:page&size=:size&sortBy=id",
			{campusid:'@_campusid', page:'@_page', size:'@_size'}, {}
			);
	}]);
