var teacherServices = angular.module('teacherServices', ['ngResource', 'nourConfig']);

teacherServices.factory('Teachers',['$resource', 'config', '$http',
	function($resource, config, $http){
		var api = {};
		api.Update = function(data,callback){
			$http.put(config.HOST + 'clazz/'+ data.id, data)
			.success(function(data, status, headers, config){
				callback(data);
			});
		};
		api.Delete = function(data, callback){
			$http.delete(config.HOST + 'teachers/'+ data)
			.success(function(data, status, headers, config){
				callback(data);
			});
		};
		return api;
	}]);

teacherServices.factory('GetTeachers', ['$resource', 'config',
	function($resource, config){
		//return $resource(config.HOST + "/campuses/:campusid/teachers?page=:page&size=:size&sortBy=id",
		return $resource(config.HOST + "/teachers/search?name=:name&campusid=:campusid&page=:page&size=:size&sortBy=id",
			{name:'@_name', campusid: '@_campusid', page:'@_page', size:'@_size'}, {}
			);
	}]);
