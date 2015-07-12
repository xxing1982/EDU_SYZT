var hotreaderServices = angular.module('hotreaderServices', ['ngResource', "nourConfig"]);

/*
    hotreader object(s)
*/
hotreaderServices.factory('Hotreader', ['$resource', 'config',
	function($resource, config){
		return $resource(config.HOST + ":by/:id/hotreaders",
            {by:'@by', id:'@id'}, {}
        );
}]);

hotreaderServices.factory('MyRecommend',['$resource', 'config', '$http',
	function($resource, config, $http){
		var api = {};
		api.get = function(teacherId, callback){
			$http.get(config.HOST + 'teacher/' + teacherId + '/recommendBooks')
			.success(function(data, status, headers, config){
				callback(data);
			});
		};
		return api;
	}]);

