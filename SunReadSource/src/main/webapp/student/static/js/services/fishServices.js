var fishServices = angular.module('fishServices', ['ngResource', "nourConfig"]);

fishServices.factory('Fish',['$resource', 'config', '$http',
	function($resource, config, $http){
		var api = {};
		api.getFishes = function(callback){
			$http.get(config.HOST + 'fishes')
			.success(function(data, status, headers, config){
				callback(data);
			});
		};
		api.getMyFish = function(studentId, callback){
			$http.get(config.HOST + 'students/' + studentId + '/fishes')
			.success(function(data, status, headers, config){
				callback(data);
			});
		}
		api.UpdateMyFish = function(studentId, fishId, callback){
			$http.put(config.HOST + 'students/' + studentId + '/fishes/' + fishId)
			.success(function(data, status, headers, config){
				callback(data);
			});
		}
		return api;
	}]);