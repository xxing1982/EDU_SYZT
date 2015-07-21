var getGiftsServices = angular.module('getGiftsServices', ['ngResource', "nourConfig"]);

/*
    getGifts object(s)
*/
getGiftsServices.factory('GetGifts', ['$resource', 'config',
	function($resource, config){
		return $resource(config.HOST + "/schools/:schoolId/gifts?page=:page&size=:size",
            {schoolId:'@_schoolId',page:'@_page', size:'@_size'}, {}
        );
}]);

getGiftsServices.factory('Gift', ['$resource', 'config',
	function($resource, config){
		return $resource(config.HOST + "gifts/:id", {}
        );
}]);

