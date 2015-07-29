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
		return $resource(config.HOST + "gifts/:id", {}, {update: { method: 'PUT' }}
        );
}]);

getGiftsServices.factory('UpdateGift', ['$resource', 'config', '$http',
	function($resource, config, $http){
		var api = {};
		api.Update = function(data, callback){
			$http.put(config.HOST + 'gifts', data)
			.success(function(data, status, headers, config){
				callback(data);
			})
		};
		return api;
	}]);