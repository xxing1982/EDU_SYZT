var tagServices = angular.module('tagServices', ['ngResource', "nourConfig"]);

// tag object(s)
tagServices.factory('Tag',['$resource', 'config',
	function($resource, config){
		return $resource(config.HOST + "tags",
            {}, { get: {isArray: true}}
        );
	}]); 

tagServices.factory('TagCategory',['$resource', 'config',
	function($resource, config){
		return $resource(config.HOST + "tags/statistics",
            {}, { get: {isArray: true}}
        );
	}]); 
