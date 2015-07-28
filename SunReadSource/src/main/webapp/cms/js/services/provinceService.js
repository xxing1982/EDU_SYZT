var provinceServices = angular.module('provinceServices', ['ngResource', 'nourConfig']);

provinceServices.factory('Province',['$resource', 'config', '$http',
	function($resource, config, $http){
		var api = {};
		api.GetProvinces = function(callback){
			$http.get(config.HOST + 'regions/provinces?page=0&size=10000')
			.success(function(data, status, headers, config){
				callback(data);
			});
		};
		api.GetSchools = function(callback){
			$http.get(config.HOST + 'campuss?page=0&size=10000&sortBy=name')
			.success(function(data, status, headers, config){
				callback(data);
			});
		};
		api.UpdateRegion = function(data, callback){
			$http.put(config.HOST + 'regions/' + data.id, data)
			.success(function(data, status, headers, config){
				callback(data);
			});
		}
		api.Add = function(data, callback){
			$http.post(config.HOST + 'regions/', data)
			.success(function(data, status, headers, config){
				callback(data);
			});
		}
		api.Delete = function(id, callback){
			$http.delete(config.HOST + 'regions/' + id)
			.success(function(data, status, headers, config){
				callback(data);
			});
		}
		return api;
	}]);
