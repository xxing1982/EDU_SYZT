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


var weeklyHotServices = angular.module('weeklyHotServices', ['ngResource', "nourConfig"]);

// Note object(s)
weeklyHotServices.factory('WeeklyHotSearch',['$resource', 'config',
	function($resource, config){
		return $resource(config.HOST+"books/conditions/weeklyhot?page=:page&size=:size"+
                         "&level=:level&category=:category&testType=:testType&literature=:literature"+
                         "&grade=:grade&language=:language&resource=:resource&pointRange=:pointRange",
                        {page:'@_page', size:'@_size',level:'@_level',category:'@_category'
                         ,testType:'@_testType',literature:'@_literature',grade:'@_grade'
                         ,language:'@_language',resource:'@_resource',pointRange:'@_pointRange'},
                        {}
        );
	}]);

var monthlyHotServices = angular.module('monthlyHotServices', ['ngResource', "nourConfig"]);

// Note object(s)
monthlyHotServices.factory('MonthlyHotSearch',['$resource', 'config',
	function($resource, config){
		return $resource(config.HOST+"books/conditions/monthlyhot?page=:page&size=:size"+
                         "&level=:level&category=:category&testType=:testType&literature=:literature"+
                         "&grade=:grade&language=:language&resource=:resource&pointRange=:pointRange",
                        {page:'@_page', size:'@_size',level:'@_level',category:'@_category'
                         ,testType:'@_testType',literature:'@_literature',grade:'@_grade'
                         ,language:'@_language',resource:'@_resource',pointRange:'@_pointRange'},
                        {}
        );
	}]);

var yearlyHotServices = angular.module('yearlyHotServices', ['ngResource', "nourConfig"]);

	// Note object(s)
	yearlyHotServices.factory('YearlyHotSearch',['$resource', 'config',
		function($resource, config){
			return $resource(config.HOST+"books/conditions/yearlyhot?page=:page&size=:size"+
	                         "&level=:level&category=:category&testType=:testType&literature=:literature"+
	                         "&grade=:grade&language=:language&resource=:resource&pointRange=:pointRange",
	                        {page:'@_page', size:'@_size',level:'@_level',category:'@_category'
	                         ,testType:'@_testType',literature:'@_literature',grade:'@_grade'
	                         ,language:'@_language',resource:'@_resource',pointRange:'@_pointRange'},
	                        {}
	        );
		}]);

var weeklyRecommendServices = angular.module('weeklyRecommendServices', ['ngResource', "nourConfig"]);

// Note object(s)
weeklyRecommendServices.factory('WeeklyRecommendSearch',['$resource', 'config',
	function($resource, config){
		return $resource(config.HOST+"books/conditions/weeklyRecommend?page=:page&size=:size"+
                         "&level=:level&category=:category&testType=:testType&literature=:literature"+
                         "&grade=:grade&language=:language&resource=:resource&pointRange=:pointRange",
                        {page:'@_page',size:'@_size',level:'@_level',category:'@_category'
                         ,testType:'@_testType',literature:'@_literature',grade:'@_grade'
                         ,language:'@_language',resource:'@_resource',pointRange:'@_pointRange'},
                        {}
        );
	}]);

var monthlyRecommendServices = angular.module('monthlyRecommendServices', ['ngResource', "nourConfig"]);

// Note object(s)
monthlyRecommendServices.factory('MonthlyRecommendSearch',['$resource', 'config',
	function($resource, config){
		return $resource(config.HOST+"books/conditions/monthlyRecommend?page=:page&size=:size"+
                         "&level=:level&category=:category&testType=:testType&literature=:literature"+
                         "&grade=:grade&language=:language&resource=:resource&pointRange=:pointRange",
                        {page:'@_page', size:'@_size',level:'@_level',category:'@_category'
                         ,testType:'@_testType',literature:'@_literature',grade:'@_grade'
                         ,language:'@_language',resource:'@_resource',pointRange:'@_pointRange'},
                        {}
        );
	}]);

var yearlyRecommendServices = angular.module('yearlyRecommendServices', ['ngResource', "nourConfig"]);

	// Note object(s)
yearlyRecommendServices.factory('YearlyRecommendSearch',['$resource', 'config',
		function($resource, config){
			return $resource(config.HOST+"books/conditions/yearlyRecommend?page=:page&size=:size"+
	                         "&level=:level&category=:category&testType=:testType&literature=:literature"+
	                         "&grade=:grade&language=:language&resource=:resource&pointRange=:pointRange",
	                        {page:'@_page', size:'@_size',level:'@_level',category:'@_category'
	                         ,testType:'@_testType',literature:'@_literature',grade:'@_grade'
	                         ,language:'@_language',resource:'@_resource',pointRange:'@_pointRange'},
	                        {}
	        );
		}]);
