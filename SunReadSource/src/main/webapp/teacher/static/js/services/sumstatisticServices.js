var sumStatisticServices = angular.module('sumStatisticServices', ['ngResource', "nourConfig"]);

// SumStatistic object(s)
sumStatisticServices.factory('SumStatistic',['$resource', 'config',
	function($resource, config){
		return $resource(config.HOST + "sumStatistics",
            {}, {}
        );
	}]); 
