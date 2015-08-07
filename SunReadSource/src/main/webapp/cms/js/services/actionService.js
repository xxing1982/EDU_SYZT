var actionServices = angular.module('actionServices', ['ngResource', "nourConfig"]);

/*
    Action object(s)
*/
actionServices.factory('Action', ['$resource', 'config',
	function($resource, config){
		return $resource(config.HOST + "actions/:id",
            {id:'@id'}, 
            {
            	update: {method:'PUT', params: {id: '@id'}},
            	del: {method:'DELETE', params: {id: '@id'}}
            }
        );
}]);
