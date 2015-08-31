var gradeServices = angular.module('gradeServices', ['ngResource', "nourConfig"]);

/*
    Grade object(s)
*/
gradeServices.factory('Grade', ['$resource', 'config',
	function($resource, config){
		return $resource(config.HOST + "grade",{}, {			
            query:{
                method: "get",
                isArray: true
            }}
        );
}]);
