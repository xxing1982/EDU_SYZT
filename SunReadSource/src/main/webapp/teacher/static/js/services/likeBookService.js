var likeServices = angular.module('likeServices', ['ngResource', "nourConfig"]);

likeServices.factory('LikeBook', ['$resource', 'config',
	function($resource, config){
		return $resource(config.HOST + "/books/:id/users/:userId/recommends",
            {id:'@id', userId:'@userId'},
            {update:
              {method:'PUT'}
            }
        );
}]);
