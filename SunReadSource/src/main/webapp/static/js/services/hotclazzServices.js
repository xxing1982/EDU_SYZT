var hotclazzServices = angular.module('hotclazzServices', ['ngResource', "nourConfig"]);

/*
    hotclazz object(s)
*/
hotclazzServices.factory('Hotclazz', ['$resource', 'config',
	function($resource, config){
		return $resource(config.HOST + ":by/:id/hotclazzs",
            {by:'@by', id:'@id'}, {}
        );
}]);
