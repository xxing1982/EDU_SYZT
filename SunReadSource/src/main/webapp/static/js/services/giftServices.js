var getGiftsServices = angular.module('getGiftsServices', ['ngResource', "nourConfig"]);

/*
    getGifts object(s)
*/
getGiftsServices.factory('GetGifts', ['$resource', 'config',
	function($resource, config){
		return $resource(config.HOST + "actions/:id",
            {id:'@id'}, {}
        );
}]);
