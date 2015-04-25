var messageServices = angular.module('messageServices', ['ngResource', "nourConfig"]);

/*
    message object(s)
*/
messageServices.factory('Messages', ['$resource', 'config',
	function($resource, config){
		return $resource(config.HOST + ":by/:id/messages",
            {by:'@by', id:'@id'}, {}
        );
}]);
