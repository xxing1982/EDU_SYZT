//readingCenterAddBookPopularRecommendCtrl.js

ctrls.controller("readingCenterAddBookPopularRecommendController", ['$scope', 'ConditionSearch'
        ,'WeeklyRecommendSearch','MonthlyRecommendSearch',function ($scope,ConditionSearch,WeeklyRecommendSearch,MonthlyRecommendSearch) {
	$scope.name='阅读中心->添加书籍->热门阅读';
	
    var pageSize = 4;
    var searchTerm='isbn';
            
    var level=0;
    var category=0;
    var testType=0;
    var literature=0;
    var grade=0;
    var category=0; 
    var language=0; 
    var resource=0;
    var pointRange=0;        
    
    $scope.level = level;
    $scope.category = category;
    $scope.testType = testType;
    $scope.literature = literature;
    $scope.grade = grade;
    $scope.category = category;
    $scope.language = language;
    $scope.resource = resource;
    $scope.pointRange = pointRange;
   $scope.statuses_grade = [{
        id: 0,
        name:"全部年级",
        callback: function(){$scope.search()}
    }, {
        id: 1,
        name: "1年级", 
        callback: function(){$scope.search()}
    }, {
        id: 2,
        name: "2年级",
        callback: function(){$scope.search()}
    }, {
        id: 3,
        name: "3年级", 
        callback: function(){$scope.search()}
    }, {
        id: 4,
        name: "4年级",
        callback: function(){$scope.search()}
    }, {
        id: 5,
        name: "5年级",
        callback: function(){$scope.search()}
    }];        
            
    $scope.statuses_category = [{
        id: 0,
        name:"全部类型",
        callback: function(){$scope.search()}
    }, {
        id: 1,
        name: "类型一",
        callback: function(){$scope.search()}
    }, {
        id: 2,
        name: "类型二", 
        callback: function(){$scope.search()}
    }, {
        id: 3,
        name: "类型三",        
        callback: function(){$scope.search()}
    }, {
        id: 4,
        name: "类型四",
        callback: function(){$scope.search()}
    }, {
        id: 5,
        name: "类型五",
        callback: function(){$scope.search()}        
    }];        
    $scope.selected_status = 0;       
                    
                    
    $scope.recommendSearch=ConditionSearch.get({page:0,size:pageSize,level:level,category:category
                            ,testType:testType,literature:literature,category:category
                            ,grade:grade,language:language,resource:resource,pointRange:pointRange}
      ,function(){
        console.log($scope.recommendSearch)
    });       
            
    $scope.search=function(){
        $scope.recommendSearch=ConditionSearch.get({page:0,size:pageSize,level:level,category:category
                                ,testType:testType,literature:literature,category:category
                                ,grade:grade,language:language,resource:resource,pointRange:pointRange}
          ,function(){
            console.log($scope.recommendSearch)
        });   
    
    };
            
	$scope.searchWeekly = function(){
        console.log(grade);
        console.log($scope.testType)
        console.log($scope.grade);
        $scope.recommendSearch=WeeklyRecommendSearch.get({page:0,size:pageSize,level:$scope.level,category:$scope.category
                                                ,testType:$scope.testType,literature:$scope.literature,category:$scope.category
                                                ,grade:$scope.grade,language:$scope.language,resource:$scope.resource,pointRange:pointRange},function(){
            console.log($scope.recommendSearch)
        });       
	};
    
    $scope.searchMonthly = function(){
        console.log(grade);
        console.log($scope.testType)
        console.log($scope.grade);
        $scope.recommendSearch=MonthlyRecommendSearch.get({page:0,size:pageSize,level:$scope.level,category:$scope.category
                                                ,testType:$scope.testType,literature:$scope.literature,category:$scope.category
                                                ,grade:$scope.grade,language:$scope.language,resource:$scope.resource,pointRange:pointRange},function(){
            console.log($scope.recommendSearch)
        });       
    };
            
    $scope.addBooktoShelf = function(terms){
        console.log(terms);
        var bookId = terms.id;
        var bookInShelf = {
            bookAttribute: true,
            readState: false
            }
        console.log(bookInShelf);
        AddbookToShelf.save({bookshelfId:1,bookId:bookId},bookInShelf);
    };
}]);