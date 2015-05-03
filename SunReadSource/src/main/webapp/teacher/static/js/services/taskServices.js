var taskServices = angular.module('taskServices', ['ngResource', "nourConfig"]);

/*
    Task object(s)
*/
taskServices.factory('Task', ['$resource', 'config',
	function($resource, config){
		return $resource(config.HOST + "teachers/:teacherId/students/:studentId/tasks",
            {by:'@teacherId', id:'@studentId'}, {
                update: { 
                    method:'PUT'
                }
            }
        );
}]);
