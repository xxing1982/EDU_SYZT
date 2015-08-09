var semesterServices = angular.module('semesterServices', ['ngResource', 'nourConfig']);

semesterServices.factory('Semester',['$resource', 'config', '$http',
	function($resource, config, $http){
		var api = {};
		api.Update = function(data, callback){
			$http.put(config.HOST + 'semester/' + data.id, data)
			.success(function(data, status, headers, config){
				callback(data);
			});
		}
		api.Delete = function(id, callback){
			$http.delete(config.HOST + 'semester/' + id)
			.success(function(data, status, headers, config){
				callback(data);
			});
		}
		api.Add = function(id, data, callback){
			$http.post(config.HOST + 'campus/' + id + '/semester' , data)
			.success(function(data, status, headers, config){
				callback(data);
			});
		}
		return api;
	}]);


semesterServices.factory('GetSemester', ['$resource', 'config',
	function($resource, config){
		return $resource(config.HOST + "campus/:campusid/semesters?page=:page&size=:size&sortBy=id&campusId=:campusid",
			{campusid: '@campusid', page:'@_page', size:'@_size'}, {}
			);
	}]);

