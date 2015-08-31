var eduGroupServices = angular.module('eduGroupServices', ['ngResource', "nourConfig"]);

eduGroupServices.factory('EduGroup',['$resource', 'config',
	function($resource, config){
		return $resource(config.HOST + "eduGroup/:id", {}, {});
	}]);