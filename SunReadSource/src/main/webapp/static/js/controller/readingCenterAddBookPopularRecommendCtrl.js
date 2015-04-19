//readingCenterAddBookPopularRecommendCtrl.js

ctrls.controller("readingCenterAddBookPopularRecommendController", ['$rootScope','$scope','$stateParams','Pageable', 'ConditionSearch','AddbookToShelf'
        ,'WeeklyRecommendSearch','MonthlyRecommendSearch',function ($rootScope,$scope,$stateParams,Pageable,ConditionSearch,AddbookToShelf,WeeklyRecommendSearch,MonthlyRecommendSearch) {
	$scope.name='阅读中心->添加书籍->热门阅读';
	
    $scope.searchArguments = {
        level:0,
        category:0,
        testType:0,
        literature:0,
        grade:0,
        category:0,
        language:0,
        resource:0,
        pointRange:0
      
    }

    $scope.createPageable = function (searchEntity){
        $scope.searchPageable = new Pageable();

        $scope.searchPageable.size = 4;
        $scope.searchPageable.page = 1;

        $scope.searchPageable.arguments=$scope.searchArguments;
        // Set the startPage and length of number page array
        
        $scope.searchPageable.pageNumbers.startPage = 1;
        $scope.searchPageable.pageNumbers.content.length = 8;
        // Set the placeholder elements
        $scope.searchPageable.placeHolders.placeHoldersElement = {title: ""};

        // Build the pageable object
        $scope.searchPageable.build(searchEntity);
        
        $scope.searchPageable.showPage($stateParams.page === undefined ? 1 : $stateParams.page);
        console.log($scope.searchPageable);
    }
            
    $scope.status = status;

    $scope.search=function(){  
        $scope.createPageable(ConditionSearch);
    } 
    
    if($scope.status === 1){
        $scope.search = $scope.searchWeekly;
    }
    if($scope.status === 2){
        $scope.search = $scope.searchMonthly;
    } 
            
    
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
            
	$scope.searchWeekly = function(){
        $scope.createPageable(WeeklyRecommendSearch);
	};
    
    $scope.searchMonthly = function(){
        $scope.createPageable(MonthlyRecommendSearch);
    };
            
    $scope.addBooktoShelf = function(terms){
        console.log(terms);
        var bookId = terms.id;
        var bookInShelf = {
            bookAttribute: false,
            readState: false
            }
        console.log(bookInShelf);
        AddbookToShelf.save({bookshelfId:$rootScope.id,bookId:bookId},bookInShelf);
    };
}]);