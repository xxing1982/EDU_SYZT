//readingCenterAddBookAdvancedSearchCtrl.js

ctrls.controller("readingCenterAddBookAdvancedSearchController", ['$scope',
        'ConditionSearch','QuickSearch',function ($scope,ConditionSearch,QuickSearch) {

    $scope.searchContent = "";
	
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
    
            
    $scope.advancedSearch=ConditionSearch.get({page:0,size:pageSize,level:level,category:category
                                                ,testType:testType,literature:literature,category:category
                                                ,grade:grade,language:language,resource:resource}
    ,function(){
        console.log($scope.advancedSearch)
    });       
            
    $scope.searchByIsbn=function(){
        $scope.advancedSearch=QuickSearch.get({page:0,size:pageSize,searchTerm:searchTerm}
                                             ,function(){
            console.log($scope.advancedSearch);
            console.log($scope.searchContent);
        })
    
    };
            
	$scope.search = function(){
        console.log(grade);
        console.log($scope.testType)
        console.log($scope.grade);
        $scope.advancedSearch=ConditionSearch.get({page:0,size:pageSize,level:$scope.level,category:$scope.category
                                                    ,testType:$scope.testType,literature:$scope.literature,category:$scope.category
                                                    ,grade:$scope.grade,language:$scope.language,resource:$scope.resource},function(){
            console.log($scope.advancedSearch)
        });       
	};
    
}]);