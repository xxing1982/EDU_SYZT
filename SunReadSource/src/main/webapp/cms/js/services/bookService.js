var bookServices = angular.module('bookServices', ['ngResource', 'nourConfig']);

// User object(s)
bookServices.factory('Book',['$resource', 'config',
	function($resource, config){
		return $resource(config.HOST + "/api/books/:id",{id: '@id'},{
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
