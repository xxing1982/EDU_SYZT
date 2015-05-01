var userServices = angular.module('userServices', ['ngResource', "nourConfig"]);

// User object(s)
userServices.factory('User',['$resource', 'config',
	function($resource, config){
		return $resource(config.HOST + "users/:id",{},{
			query: {
				method: "get",
				isArray: true
			},
            update: { 
                method:'PUT'
            }
		});
	}]);

userServices.factory('Teacher',['$resource', 'config',
	function($resource, config){
		return $resource(config.HOST + "teachers/:id",{},{
			query:{
				method: "get",
				isArray: true
			}
		});
	}]);

