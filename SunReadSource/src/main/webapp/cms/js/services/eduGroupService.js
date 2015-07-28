var adminServices = angular.module('eduGropuServices', ['ngResource', 'nourConfig']);

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

adminServices.factory('GetEduGroup', ['$resource', 'config',
	function($resource, config){
		return $resource(config.HOST + "eduGroups?page=:page&size=:size&sortBy=id",
			{page:'@_page', size:'@_size'}, {}
			);
	}]);
