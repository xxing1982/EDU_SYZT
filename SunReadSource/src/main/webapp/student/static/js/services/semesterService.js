var semesterServices = angular.module('semesterServices', ['ngResource', "nourConfig"]);

/*
    Semester object(s)
*/
semesterServices.factory('Semester', ['$resource', 'config',
	function($resource, config){
		return $resource(config.HOST + ":by/:id/semesters",
            { by:'@by', id:'@id' }, 
            { get: {method: 'get', isArray: true}
            }
        );
}]);
