var schoolDistrictServices = angular.module('schoolDistrictServices', ['ngResource', "nourConfig"]);

schoolDistrictServices.factory('SchoolDistrict',['$resource', 'config',
	function($resource, config){
		return $resource(config.HOST + "schoolDistrict/:id", {}, {});
	}]);