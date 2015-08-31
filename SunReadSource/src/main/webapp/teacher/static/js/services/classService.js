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

classServices.factory('Classes',['$resource', 'config',
	function($resource, config){
		return $resource(config.HOST + ":by/:id/clazzs",
            { by:'@by', id:'@id' }, { 
                get: {
				    method: "get",
				    isArray: true
			}
		});
	}]);




