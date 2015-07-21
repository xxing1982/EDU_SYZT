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
		api.Update = function(userid, oldpassword, newpassword, callback){
			$http.put(config.HOST + 'systemadmin?userid=' + userid + "&oldpassword=" + oldpassword + "&newpassword=" + newpassword)
			.success(function(data, status, headers, config){
				callback(data);
			})
			.error(function(data,header,config,status){
				callback(data);
			});
		};
		api.Delete = function(data, callback){
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
			$http.get(config.HOST + 'superschooladmin')
			.success(function(data, status, headers, config){
				callback(data);
			});
		};
		api.Add = function(campusid, userid, password, callback){
			$http.post(config.HOST + 'superschooladmin?campusid=' + campusid + '&userid=' + userid + "&password=" + password)
			.success(function(data, status, headers, config){
				callback(data);
			})
			.error(function(data,header,config,status){
				callback(data);
			});
		};
		api.Update = function(userid, oldpassword, newpassword, callback){
			$http.put(config.HOST + 'superschooladmin?userid=' + userid + "&oldpassword=" + oldpassword + "&newpassword=" + newpassword)
			.success(function(data, status, headers, config){
				callback(data);
			})
			.error(function(data,header,config,status){
				callback(data);
			});
		};
		api.Delete = function(data, callback){
			$http.delete(config.HOST + 'superschooladmin?id='+ data)
			.success(function(data, status, headers, config){
				callback(data);
			})
			.error(function(data,header,config,status){
				callback(data);
			});
		};
		return api;
	}]);

adminServices.factory('GetSuperSchoolAdmin', ['$resource', 'config',
	function($resource, config){
		return $resource(config.HOST + "superschooladmins?campusid=:campusid&page=:page&size=:size&sortBy=id",
			{campusid:'@_campusid', page:'@_page', size:'@_size'}, {}
			);
	}]);

adminServices.factory('SchoolAdmin',['$resource', 'config', '$http',
	function($resource, config, $http){
		var api = {};
		api.Get = function(callback){
			$http.get(config.HOST + 'schooladmin')
			.success(function(data, status, headers, config){
				callback(data);
			});
		};
		api.Add = function(campusid, userid, password, callback){
			$http.post(config.HOST + 'schooladmin?campusid=' + campusid + '&userid=' + userid + "&password=" + password)
			.success(function(data, status, headers, config){
				callback(data);
			})
			.error(function(data,header,config,status){
				callback(data);
			});
		};
		api.Update = function(userid, oldpassword, newpassword, callback){
			$http.put(config.HOST + 'schooladmin?userid=' + userid + "&oldpassword=" + oldpassword + "&newpassword=" + newpassword)
			.success(function(data, status, headers, config){
				callback(data);
			})
			.error(function(data,header,config,status){
				callback(data);
			});
		};
		api.Delete = function(data, callback){
			$http.delete(config.HOST + 'schooladmin?id='+ data)
			.success(function(data, status, headers, config){
				callback(data);
			})
			.error(function(data,header,config,status){
				callback(data);
			});
		};
		return api;
	}]);

adminServices.factory('GetSchoolAdmin', ['$resource', 'config',
	function($resource, config){
		return $resource(config.HOST + "schooladmins?campusid=:campusid&page=:page&size=:size&sortBy=id",
			{campusid:'@_campusid', page:'@_page', size:'@_size'}, {}
			);
	}]);