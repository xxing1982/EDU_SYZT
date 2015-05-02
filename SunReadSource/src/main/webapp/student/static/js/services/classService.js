var classServices = angular.module('classServices', ['ngResource', "nourConfig"]);

// User object(s)
classServices.factory('Class',['$resource', 'config',
	function($resource, config){
		return $resource(config.HOST + "clazz/:id",{},{
			query:{
				method: "get",
				isArray: true
			}
		});
	}]);


