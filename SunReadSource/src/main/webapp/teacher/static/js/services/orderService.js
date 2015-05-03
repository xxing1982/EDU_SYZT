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
