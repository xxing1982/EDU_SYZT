var reviewServices = angular.module('reviewServices', ['ngResource', "nourConfig"]);

// Comment object(s)
reviewServices.factory('Review',['$resource', 'config',
	function($resource, config){
		return $resource(config.HOST + ":by/:id/reviews",
            {by:'@by', id:'@id'}, {}
        );
	}]); 