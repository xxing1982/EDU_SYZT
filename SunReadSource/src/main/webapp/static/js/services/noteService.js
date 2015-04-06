var noteServices = angular.module('noteServices', ['ngResource', "nourConfig"]);

// Note object(s)
noteServices.factory('Note',['$resource', 'config',
	function($resource, config){
		return $resource("/api/notes?page=:page&size=:size&sortBy=:sortBy",
                        {page:'@_page', size:'@_size', sortBy:"@_sort"},
                        {}
        );
	}]);
