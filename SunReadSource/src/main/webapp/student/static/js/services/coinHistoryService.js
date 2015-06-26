var coinHistoryServices = angular.module('coinHistoryServices', ['ngResource', "nourConfig"]);

/*
    Coinhistory object(s)
*/
coinHistoryServices.factory('CoinHistory', ['$resource', 'config',
	function($resource, config){
		return $resource(config.HOST + ":by/:id/coinhistories",
            { by:'@by', id:'@id' }, 
            {}
        );
}]);
