var popularSearchServices = angular.module('popularSearchServices', ['ngResource', "nourConfig"]);

// WeeklyHotSearch object(s)
popularSearchServices.factory('WeeklyHotSearch',['$resource', 'config',
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

  // MonthlyHotSearch object(s)
  popularSearchServices.factory('MonthlyHotSearch',['$resource', 'config',
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

  	// YearlyHotSearch object(s)
    popularSearchServices.factory('YearlyHotSearch',['$resource', 'config',
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

  // WeeklyRecommendSearch object(s)
  popularSearchServices.factory('WeeklyRecommendSearch',['$resource', 'config',
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

  // MonthlyRecommendSearch object(s)
  popularSearchServices.factory('MonthlyRecommendSearch',['$resource', 'config',
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

  	// YearlyRecommendSearch object(s)
    popularSearchServices.factory('YearlyRecommendSearch',['$resource', 'config',
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
