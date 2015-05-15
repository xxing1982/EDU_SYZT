var recommendServices = angular.module('recommendServices', ['ngResource', "nourConfig"]);

/*
    Note object(s)
*/
recommendServices.factory('GetRecommends', ['$resource', 'config',
	function($resource, config){
		return $resource(config.HOST + ":by/:id/recommends?page=:page&size=:size",
            {by:'@by', id:'@id',page:'page',size:'size'}, {}
        );
}]);

recommendServices.factory('AddRecommends', ['$resource', 'config',
	function($resource, config){
		return $resource(config.HOST + "teacher/:teacherId/student/:studentId/recommend",
            {teacherId:'@teacherId', studentId:'@studentId'}, {}
        );
}]);
