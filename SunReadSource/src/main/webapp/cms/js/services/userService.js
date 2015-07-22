var userServices = angular.module('userServices', ['ngResource', 'nourConfig']);

// User object(s)
userServices.factory('User',['$resource', 'config',
	function($resource, config){
		return $resource(config.HOST + "/users/:id",{id: '@id'},{
			query:{
				method: 'get',
				isArray: true,
				params:{
					page: '@page',
					size: '@size'
				}
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