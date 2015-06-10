var booktagServices = angular.module('booktagServices', ['ngResource', "nourConfig"]);

// Booktag object(s)
booktagServices.factory('Booktag',['$resource', 'config',
	function($resource, config){
		return $resource(config.HOST + "booktags",
            {}, {}
        );
	}]); 
