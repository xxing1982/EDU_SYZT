//readingCenterAddBookPopularReadingCtrl.js

ctrls.controller("readingCenterAddBookPopularReadingController", ['$scope', 'ConditionSearch','AddbookToShelf'
        ,'WeeklyHotSearch','MonthlyHotSearch',function ($scope,ConditionSearch,AddbookToShelf
                                                         ,WeeklyHotSearch,MonthlyHotSearch) {
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
            
    $scope.statuses = [{
        id: 1,
        name:"全部年级"
    }, {
        id: 2,
        name: "1年级"        
    }, {
        id: 3,
        name: "2年级"        
    }, {
        id: 4,
        name: "3年级"        
    }, {
        id: 5,
        name: "4年级"        
    }, {
        id: 6,
        name: "5年级"        
    }];        
    $scope.selected_status = 1;
                    
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