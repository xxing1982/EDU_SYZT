var bookshelfServices = angular.module('bookshelfServices', ['ngResource', "nourConfig"]);

bookshelfServices.factory('GetBookshelvesByClass',['$resource', 'config',
	function($resource, config){
        return $resource(config.HOST + "class/:classId/bookshelves?page=:page&size=:size",
            {classId:'@classId', page:'@page',size:'@size'}, {}
        );
	}]);
