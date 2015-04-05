var userServices = angular.module('userServices', ['ngResource', "nourConfig"]);

// User object(s)
userServices.factory('User',['$resource', 'config',
	function($resource, config){
		return $resource(config.HOST + "users/:id",{},{
			query:{
				method: "get",
				isArray: true
			}
		});
	}]);

userServices.factory('Student',['$resource', 'config',
	function($resource, config){
		return $resource(config.HOST + "students/:id",{},{
			query:{
				method: "get",
				isArray: true
			}
		});
	}]);

