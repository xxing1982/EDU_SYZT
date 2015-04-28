var getGiftsServices = angular.module('getGiftsServices', ['ngResource', "nourConfig"]);

/*
    getGifts object(s)
*/
getGiftsServices.factory('GetGifts', ['$resource', 'config',
	function($resource, config){
		return $resource(config.HOST + "gifts?page=:page&size=:size",
            {page:'@_page', size:'@_size'}, {}
        );
}]);


var getGiftsExNumServices = angular.module('getGiftsExNumServices', ['ngResource', "nourConfig"]);

/*
    getGifts object(s)
*/
getGiftsExNumServices.factory('GetGiftsExNum', ['$resource', 'config',
	function($resource, config){
		return $resource(config.HOST + "students/:id/exchanges?page=:page&size=:size",
            {id:'@_id', page:'@_page', size:'@_size'}, {}
        );
}]);
