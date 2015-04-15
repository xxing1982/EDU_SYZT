//readingCenterAddBookAdvancedSearchCtrl.js

ctrls.controller("readingCenterAddBookAdvancedSearchController", ['$scope',
        'ConditionSearch','QuickSearch',function ($scope,ConditionSearch,QuickSearch) {

    
    $scope.searchContent = "";
	
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
        name:"全部年级"
    }, {
        id: 1,
        name: "1年级"        
    }, {
        id: 2,
        name: "2年级"        
    }, {
        id: 3,
        name: "3年级"        
    }, {
        id: 4,
        name: "4年级"        
    }, {
        id: 5,
        name: "5年级"        
    }];        
            
    $scope.statuses_category = [{
        id: 0,
        name:"全部类型"
    }, {
        id: 1,
        name: "类型一"        
    }, {
        id: 2,
        name: "类型二"        
    }, {
        id: 3,
        name: "类型三"        
    }, {
        id: 4,
        name: "类型四"        
    }, {
        id: 5,
        name: "类型五"        
    }];        
    $scope.selected_status = 0;        
            
            
    $scope.advancedSearch=ConditionSearch.get({page:0,size:pageSize,level:level,category:category
                                                ,testType:testType,literature:literature,category:category
                                                ,grade:grade,language:language,resource:resource,pointRange:pointRange}
    ,function(){
        console.log($scope.advancedSearch)
    });       
            
    $scope.searchByName=function(){
        $scope.advancedSearch=QuickSearch.get({page:0,size:pageSize,searchTerm:$scope.searchContent}
                                             ,function(){
            console.log($scope.advancedSearch);
            console.log($scope.searchContent);
        })
    
    };
            
	$scope.search = function(){
        console.log();
        console.log($scope.testType)
        console.log($scope.grade);
        $scope.advancedSearch=ConditionSearch.get({page:0,size:pageSize,level:$scope.level,category:$scope.category
                                                    ,testType:$scope.testType,literature:$scope.literature,category:$scope.category
                                                    ,grade:$scope.grade,language:$scope.language,resource:$scope.resource,pointRange:$scope.pointRange},function(){
            console.log($scope.advancedSearch)
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
