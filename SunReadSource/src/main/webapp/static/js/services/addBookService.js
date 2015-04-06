var lackFeedbackServices = angular.module('lackFeedbackServices', ['ngResource', "nourConfig"]);

// Note object(s)
lackFeedbackServices.factory('LackFeedback',['$resource', 'config',
	function($resource, config){
		return $resource("/api/supplementbooks",
                        {},
                        {}
        );
	}]);

var conditionSearchServices = angular.module('conditionSearchServices', ['ngResource', "nourConfig"]);

// Note object(s)
conditionSearchServices.factory('ConditionSearch',['$resource', 'config',
	function($resource, config){
		return $resource("/api/books/conditions?page=:page&size=:size&sortBy=id"+
                         "&level=:level&category=:category&testType=:testType&literature=:literature"+
                         "&grade=:grade&language=:language&resource=:resource",
                        {page:'@_page', size:'@_size',level:'@_level',category:'@_category'
                         ,testType:'@_testType',literature:'@_literature',grade:'@_grade'
                         ,language:'@_language',resource:'@_resource'},
                        {}
        );
	}]);

var quickSearchServices = angular.module('quickSearchServices',['ngResource',"nourConfig"]);

quickSearchServices.factory('QuickSearch',['$resource', 'config',
	function($resource, config){
		return $resource(
            "/api/books/search?page=:page&size=:size&sortBy=id&searchTerm=:searchTerm"
            ,{page:'@_page', size:'@_size',searchTerm:'@_searchTerm'},{}
            );
	}]);


var weeklyHotServices = angular.module('weeklyHotServices', ['ngResource', "nourConfig"]);

// Note object(s)
weeklyHotServices.factory('WeeklyHotSearch',['$resource', 'config',
	function($resource, config){
		return $resource("/api/books/conditions/weeklyhot?page=:page&size=:size&sortBy=id"+
                         "&level=:level&category=:category&testType=:testType&literature=:literature"+
                         "&grade=:grade&language=:language&resource=:resource",
                        {page:'@_page', size:'@_size',level:'@_level',category:'@_category'
                         ,testType:'@_testType',literature:'@_literature',grade:'@_grade'
                         ,language:'@_language',resource:'@_resource'},
                        {}
        );
	}]);

var monthlyHotServices = angular.module('monthlyHotServices', ['ngResource', "nourConfig"]);

// Note object(s)
monthlyHotServices.factory('MonthlyHotSearch',['$resource', 'config',
	function($resource, config){
		return $resource("/api/books/conditions/monthlyhot?page=:page&size=:size&sortBy=id"+
                         "&level=:level&category=:category&testType=:testType&literature=:literature"+
                         "&grade=:grade&language=:language&resource=:resource",
                        {page:'@_page', size:'@_size',level:'@_level',category:'@_category'
                         ,testType:'@_testType',literature:'@_literature',grade:'@_grade'
                         ,language:'@_language',resource:'@_resource'},
                        {}
        );
	}]);

var weeklyRecommendServices = angular.module('weeklyRecommendServices', ['ngResource', "nourConfig"]);

// Note object(s)
weeklyRecommendServices.factory('WeeklyRecommendSearch',['$resource', 'config',
	function($resource, config){
		return $resource("/api/books/conditions/weeklyRecommend?page=:page&size=:size&sortBy=id"+
                         "&level=:level&category=:category&testType=:testType&literature=:literature"+
                         "&grade=:grade&language=:language&resource=:resource",
                        {page:'@_page', size:'@_size',level:'@_level',category:'@_category'
                         ,testType:'@_testType',literature:'@_literature',grade:'@_grade'
                         ,language:'@_language',resource:'@_resource'},
                        {}
        );
	}]);

var monthlyRecommendServices = angular.module('monthlyRecommendServices', ['ngResource', "nourConfig"]);

// Note object(s)
monthlyRecommendServices.factory('MonthlyRecommendSearch',['$resource', 'config',
	function($resource, config){
		return $resource("/api/books/conditions/monthlyRecommend?page=:page&size=:size&sortBy=id"+
                         "&level=:level&category=:category&testType=:testType&literature=:literature"+
                         "&grade=:grade&language=:language&resource=:resource",
                        {page:'@_page', size:'@_size',level:'@_level',category:'@_category'
                         ,testType:'@_testType',literature:'@_literature',grade:'@_grade'
                         ,language:'@_language',resource:'@_resource'},
                        {}
        );
	}]);