var tagServices = angular.module('tagServices', ['ngResource', "nourConfig"]);

// tag object(s)
tagServices.factory('Tag',['$resource', 'config',
	function($resource, config){
		return $resource(config.HOST + "tags",
            {}, { get: {isArray: true}}
        );
	}]); 
