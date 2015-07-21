var studentServices = angular.module('studentServices', ['ngResource', 'nourConfig']);

studentServices.factory('GetStudent',['$resource', 'config', '$http',
	function($resource, config){
		return $resource(config.HOST + "campus/:campusid/hotreaders?page=:page&size=:size&sortBy=id",
			{campusid:'@_campusid', page:'@_page', size:'@_size'}, {}
			);
	}]);
