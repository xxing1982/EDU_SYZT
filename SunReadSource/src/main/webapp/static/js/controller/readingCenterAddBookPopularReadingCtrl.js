//readingCenterAddBookPopularReadingCtrl.js

ctrls.controller("readingCenterAddBookPopularReadingController", ['$scope', 'ConditionSearch'
        ,'WeeklyHotSearch','MonthlyHotSearch',function ($scope,ConditionSearch,WeeklyHotSearch,MonthlyHotSearch) {
	$scope.name='阅读中心->添加书籍->热门阅读';
	
    var pageSize = 5;
    var searchTerm='isbn';
            
    var level=0;
    var category=0;
    var testType=0;
    var literature=0;
    var grade=0;
    var category=0; 
    var language=0; 
    var resource=0; 
    
    $scope.level = level;
    $scope.category = category;
    $scope.testType = testType;
    $scope.literature = literature;
    $scope.grade = grade;
    $scope.category = category;
    $scope.language = language;
    $scope.resource = resource;
                    
                    
    $scope.popularSearch=ConditionSearch.get({page:0,size:pageSize,level:level,category:category
                            ,testType:testType,literature:literature,category:category
                            ,grade:grade,language:language,resource:resource}
      ,function(){
        console.log($scope.popularSearch)
    });       
            
    $scope.search=function(){
        $scope.popularSearch=ConditionSearch.get({page:0,size:pageSize,level:level,category:category
                                ,testType:testType,literature:literature,category:category
                                ,grade:grade,language:language,resource:resource}
          ,function(){
            console.log($scope.popularSearch)
        });   
    
    };
            
	$scope.searchWeekly = function(){
        console.log(grade);
        console.log($scope.testType)
        console.log($scope.grade);
        $scope.popularSearch=WeeklyHotSearch.get({page:0,size:pageSize,level:$scope.level,category:$scope.category
                                                ,testType:$scope.testType,literature:$scope.literature,category:$scope.category
                                                ,grade:$scope.grade,language:$scope.language,resource:$scope.resource},function(){
            console.log($scope.popularSearch)
        });       
	};
    
    	$scope.searchMonthly = function(){
        console.log(grade);
        console.log($scope.testType)
        console.log($scope.grade);
        $scope.popularSearch=MonthlyHotSearch.get({page:0,size:pageSize,level:$scope.level,category:$scope.category
                                                ,testType:$scope.testType,literature:$scope.literature,category:$scope.category
                                                ,grade:$scope.grade,language:$scope.language,resource:$scope.resource},function(){
            console.log($scope.popularSearch)
        });       
	};
    
}]);