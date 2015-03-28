var commentServices = angular.module('commentServices', ['ngResource', "nourConfig"]);

// Comment object(s)
noteServices.factory('Comment',['$resource', 'config',
	function($resource, config){
		return $resource("http://127.0.0.1:8080/api/notes/:id/comments?page=0&size=10&sortBy=id",{id:'@_id'},{
            getByNoteId: {
                method: 'GET'
            }
        });
	}]); 
