var adminServices = angular.module('adminServices', ['ngResource', 'nourConfig']);

adminServices.factory('SystemAdmin',['$resource', 'config', '$http',
	function($resource, config, $http){
		var api = {};
		api.Get = function(callback){
			$http.get(config.HOST + 'SystemAdmin')
			.success(function(data, status, headers, config){
				callback(data);
			});
		};
		api.Add = function(callback){
			$http.post(config.HOST + 'Systemadmin')
			.success(function(data, status, headers, config){
				callback(data);
			});
		};
		api.Update = function(data ,callback){
			$http.put(config.HOST + 'Systemadmin', data)
			.success(function(data, status, headers, config){
				callback(data);
			});
		};
		api.Delete = function(data ,callback){
			$http.delete(config.HOST + 'Systemadmin?id='+ data)
			.success(function(data, status, headers, config){
				callback(data);
			});
		};
		return api;
	}]);

adminServices.factory('SuperSchoolAdmin',['$resource', 'config', '$http',
	function($resource, config, $http){
		var api = {};
		api.Get = function(callback){
			$http.get(config.HOST + 'superschooladmins')
			.success(function(data, status, headers, config){
				callback(data);
			});
		};
		api.Add = function(callback){
			$http.post(config.HOST + 'superschooladmin')
			.success(function(data, status, headers, config){
				callback(data);
			});
		};
		api.Update = function(data ,callback){
			$http.put(config.HOST + 'superschooladmin', data)
			.success(function(data, status, headers, config){
				callback(data);
			});
		};
		api.Delete = function(data ,callback){
			$http.delete(config.HOST + 'superschooladmin?id='+ data)
			.success(function(data, status, headers, config){
				callback(data);
			});
		};
		return api;
	}]);

adminServices.factory('SchoolAdmin',['$resource', 'config', '$http',
	function($resource, config, $http){
		var api = {};
		api.Get = function(callback){
			$http.get(config.HOST + 'schooladmins')
			.success(function(data, status, headers, config){
				callback(data);
			});
		};
		api.Add = function(callback){
			$http.post(config.HOST + 'schooladmin')
			.success(function(data, status, headers, config){
				callback(data);
			});
		};
		api.Update = function(data ,callback){
			$http.put(config.HOST + 'schooladmin', data)
			.success(function(data, status, headers, config){
				callback(data);
			});
		};
		api.Delete = function(data ,callback){
			$http.delete(config.HOST + 'schooladmin?id='+ data)
			.success(function(data, status, headers, config){
				callback(data);
			});
		};
		return api;
	}]);