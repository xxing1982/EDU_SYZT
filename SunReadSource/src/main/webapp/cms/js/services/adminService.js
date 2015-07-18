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
		api.Add = function(userid, password, callback){
			$http.post(config.HOST + 'systemadmin?userid=' + userid + "&password=" + password)
			.success(function(data, status, headers, config){
				callback(data);
			})
			.error(function(data,header,config,status){
				callback(data);
			});
		};
		api.Update = function(userid, oldpassword, newpassword ,callback){
			$http.put(config.HOST + 'systemadmin?userid=' + userid + "&oldpassword=" + oldpassword + "&newpassword=" + newpassword)
			.success(function(data, status, headers, config){
				callback(data);
			})
			.error(function(data,header,config,status){
				callback(data);
			});
		};
		api.Delete = function(data ,callback){
			$http.delete(config.HOST + 'systemadmin?id='+ data)
			.success(function(data, status, headers, config){
				callback(data);
			})
			.error(function(data,header,config,status){
				callback(data);
			});
		};
		return api;
	}]);

adminServices.factory('GetSystemAdmin', ['$resource', 'config',
	function($resource, config){
		return $resource(config.HOST + "systemadmin?page=:page&size=:size&sortBy=id",
			{page:'@_page', size:'@_size'}, {}
			);
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