var commentServices = angular.module('commentServices', ['ngResource', "nourConfig"]);

// Comment object(s)
commentServices.factory('Comment',['$resource', 'config',
	function($resource, config){
		return $resource(config.HOST + ":by/:id/comments",
            {by:'@by', id:'@id'}, {}
        );
	}]); 
