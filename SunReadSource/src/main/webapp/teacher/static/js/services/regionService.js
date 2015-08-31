var regionServices = angular.module('regionServices', ['ngResource', "nourConfig"]);

regionServices.factory('Region',['$resource', 'config',
	function($resource, config){
		return $resource(config.HOST + "regions/:id", {}, {});
	}]);