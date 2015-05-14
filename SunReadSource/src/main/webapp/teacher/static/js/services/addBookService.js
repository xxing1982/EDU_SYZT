var lackFeedbackServices = angular.module('lackFeedbackServices', ['ngResource', "nourConfig"]);

// Note object(s)
lackFeedbackServices.factory('LackFeedback',['$resource', 'config',
	function($resource, config){
		return $resource(config.HOST+"supplementbooks",
                        {},
                        {}
        );
	}]);

var conditionSearchServices = angular.module('conditionSearchServices', ['ngResource', "nourConfig"]);

// Note object(s)
conditionSearchServices.factory('ConditionSearch',['$resource', 'config',
	function($resource, config){
		return $resource(config.HOST+"books/conditions?page=:page&size=:size&sortBy=id"+
                         "&level=:level&category=:category&testType=:testType&literature=:literature"+
                         "&grade=:grade&language=:language&resource=:resource&pointRange=:pointRange&searchTerm=:searchTerm",
                        {page:'@_page', size:'@_size',level:'@_level',category:'@_category'
                         ,testType:'@_testType',literature:'@_literature',grade:'@_grade'
                         ,language:'@_language',resource:'@_resource',pointRange:'@_pointRange',searchTerm:'@_searchTerm'},
                        {}
        );
	}]);

var quickSearchServices = angular.module('quickSearchServices',['ngResource',"nourConfig"]);

quickSearchServices.factory('QuickSearch',['$resource', 'config',
	function($resource, config){
		return $resource(
							config.HOST+"books/conditions?page=:page&size=:size&sortBy=:sortBy&direction=desc"
            ,{page:'@_page', size:'@_size',sortBy:'@_sortBy'},{}
            );
	}]);
