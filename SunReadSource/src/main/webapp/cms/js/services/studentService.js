var studentServices = angular.module('studentServices', ['ngResource', 'nourConfig']);

studentServices.factory('Student',['$resource', 'config', '$http',
	function($resource, config, $http){
		var api = {};
		api.GetStudents = function(callback){
			$http.get(config.HOST + 'student')
			.success(function(data, status, headers, config){
				callback(data);
			});
		};
		return api;
	}]);
