var pointHistoryServices = angular.module('pointHistoryServices', ['ngResource', "nourConfig"]);

/*
    pointhistory object(s)
*/
pointHistoryServices.factory('PointHistory', ['$resource', 'config',
	function($resource, config){
		return $resource(config.HOST + ":by/:id/pointhistories",
            { by:'@by', id:'@id' }, 
            {}
        );
}]);
