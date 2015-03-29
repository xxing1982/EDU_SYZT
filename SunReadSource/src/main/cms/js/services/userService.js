var userServices = angular.module('userServices', ['ngResource', 'nourConfig']);

// User object(s)
userServices.factory('User',['$resource', 'config',
	function($resource, config){
		return $resource(config.HOST + "/api/users/:id",{id: '@id'},{
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
