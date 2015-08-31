var campusServices = angular.module('campusServices', ['ngResource', "nourConfig"]);

/*
    campus object(s)
*/
campusServices.factory('Campus', ['$resource', 'config',
	function($resource, config){
		return $resource(config.HOST + "campus/:id",
            {by:'@by'}, {}
        );
}]);

campusServices.factory('Campuses', ['$resource', 'config',
	function($resource, config){
		return $resource(config.HOST + ":by/:id/campus",
            {by:'@by', id:'@id'}, {
                get: {
                    method: "get",
                    isArray: true
                }
            }
        );
}]);
