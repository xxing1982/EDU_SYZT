var teacherServices = angular.module('teacherServices', ['ngResource', "nourConfig"]);

/*
    teacher object(s)
*/
teacherServices.factory('TeachersFromCampus', ['$resource', 'config',
	function($resource, config){
		return $resource(config.HOST + "campuses/:campusId/teachers?page=:page&size=:size",
            {campusId:'@campusId', page:'@page',size:'@size'}, {}
        );
}]);
