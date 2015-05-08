var dictionariesService = angular.module('dictionariesService', ['ngResource', "nourConfig"]);

/*
  dictionaries object(s)
*/
dictionariesService.factory('Dictionary', ['$resource', 'config',
	function($resource, config){
		return $resource(config.HOST + "dictionaries?type=:type",
            {type:'@type'}, {}
        );
}]);
