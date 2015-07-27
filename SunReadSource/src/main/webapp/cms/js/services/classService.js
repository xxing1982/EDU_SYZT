var classServices = angular.module('classServices', ['ngResource', 'nourConfig']);

adminServices.factory('Clazzs',['$resource', 'config', '$http',
	function($resource, config, $http){
		var api = {};
		api.Add = function(id, data,callback){
			$http.post(config.HOST + 'campus/'+ id + '/clazz', data)
			.success(function(data, status, headers, config){
				callback(data);
			});
		};
		api.Update = function(id, data,callback){
			$http.put(config.HOST + 'clazz/'+ id, data)
			.success(function(data, status, headers, config){
				callback(data);
			});
		};
		api.Delete = function(data, callback){
			$http.delete(config.HOST + 'clazz/'+ data)
			.success(function(data, status, headers, config){
				callback(data);
			});
		};
		api.UpdateGread = function(classid, callback){
			$http.put(config.HOST + 'clazzs/'+ classid + '/upgrade')
			.success(function(data, status, headers, config){
				callback(data);
			});
		};
		return api;
	}]);

adminServices.factory('GetClazzs', ['$resource', 'config',
	function($resource, config){
		return $resource(config.HOST + "/campuses/:campusid/hotclazzs?page=:page&size=:size&sortBy=id",
			{campusid:'@_campusid', page:'@_page', size:'@_size'}, {}
			);
	}]);
