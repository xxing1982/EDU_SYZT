var noteServices = angular.module('noteServices', ['ngResource', "nourConfig"]);

/*
    Note object(s)
*/
noteServices.factory('Note', ['$resource', 'config',
	function($resource, config){
		return $resource(config.HOST + ":by/:id/notes",
            {by:'@by', id:'@id'}, {}
        );
}]);
