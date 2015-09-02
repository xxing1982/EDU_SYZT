var orderServices = angular.module('orderServices', ['ngResource', "nourConfig"]);

/*
    Order object(s)
*/
orderServices.factory('Order', ['$resource', 'config',
	function($resource, config){
		return $resource(config.HOST + "students/:by/:id/orders",
            {by:'@by', id:'@id'}, {}
        );
}]);

orderServices.factory('CampusOrder', ['$resource', 'config',
	function($resource, config){
		return $resource(config.HOST + "campus/:by/:id/orders",
            {by:'@by', id:'@id'}, {}
        );
}]);
