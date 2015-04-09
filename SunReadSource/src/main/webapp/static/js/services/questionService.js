var questionServices = angular.module('questionServices', ['ngResource', "nourConfig"]);


questionServices.factory('Question',['$resource', 'config',
	function($resource, config){
		return $resource(config.HOST + "objectivequestion",{},{
			query:{
				method: "get",
				isArray: true
			}
		});
	}]);

questionServices.factory('Option',['$resource', 'config', '$http',
	function($resource, config, $http){
		var api = {};
		api.put = function(questionId, optionId, callback){
			$http.put(config.HOST + 'questionoption/'+questionId + '/' + optionId)
			.success(function(data, status, headers, config){
				callback(data);
			});
		};
		return api;
	}]);

