ctrls.controller("teachingCenterAddBookSearchController", ['$scope','$stateParams','config','QuickSearch','ConditionSearch'
        ,'WeeklyHotSearch','MonthlyHotSearch','YearlyHotSearch','WeeklyRecommendSearch','MonthlyRecommendSearch','YearlyRecommendSearch'
        ,function ($scope,$stateParams,config,QuickSearch,ConditionSearch
          ,WeeklyHotSearch,MonthlyHotSearch,YearlyHotSearch,WeeklyRecommendSearch,MonthlyRecommendSearch,YearlyRecommendSearch){
        $scope.imageServer=config.IMAGESERVER; ;

//QuickSearch
    $scope.hots=QuickSearch.get({page:0,size:3,sortBy:"statistic.readNums"},function(){
      console.log($scope.hots);
    })
    //
    $scope.recommends=QuickSearch.get({page:0,size:3,sortBy:"statistic.recommends"},function(){
      console.log($scope.hots);
    })


 //AdvancedSearch

 var pageSize = 4;
 var searchTerm='isbn';

 $scope.searchArguments = {
     level:0,
     category:0,
     testType:0,
     literature:0,
     grade:0,
     category:0,
     language:0,
     resource:0,
     pointRange:0,
     searchTerm:""
   }

//    $scope.searchContent = searchContent;
 $scope.statuses_grade = [{
     id: 0,
     name:"全部年级",
     callback: function(){$scope.search($scope.searchArguments)}
 }, {
     id: 1,
     name: "1年级",
     callback: function(){$scope.search($scope.searchArguments)}
 }, {
     id: 2,
     name: "2年级",
     callback: function(){$scope.search($scope.searchArguments)}
 }, {
     id: 3,
     name: "3年级",
     callback: function(){$scope.search($scope.searchArguments)}
 }, {
     id: 4,
     name: "4年级",
     callback: function(){$scope.search($scope.searchArguments)}
 }, {
     id: 5,
     name: "5年级",
     callback: function(){$scope.search($scope.searchArguments)}
 }];

 $scope.statuses_category = [{
     id: 0,
     name:"全部类型",
     callback: function(){$scope.search($scope.searchArguments)}
 }, {
     id: 1,
     name: "类型一",
     callback: function(){$scope.search($scope.searchArguments)}
 }, {
     id: 2,
     name: "类型二",
     callback: function(){$scope.search($scope.searchArguments)}
 }, {
     id: 3,
     name: "类型三",
     callback: function(){$scope.search($scope.searchArguments)}
 }, {
     id: 4,
     name: "类型四",
     callback: function(){$scope.search($scope.searchArguments)}
 }, {
     id: 5,
     name: "类型五",
     callback: function(){$scope.search($scope.searchArguments)}
 }];
 $scope.selected_status = 0;

 $scope.createPageable = function (searchEntity){
     $scope.searchPageable = new Pageable();

     $scope.searchPageable.size = 4;
     $scope.searchPageable.page = 1;

     $scope.searchPageable.arguments=$scope.searchArguments;
     // Set the startPage and length of number page array
     console.log($scope.searchArguments);

     $scope.searchPageable.pageNumbers.startPage = 1;
     $scope.searchPageable.pageNumbers.content.length = 8;
     // Set the placeholder elements
     $scope.searchPageable.placeHolders.placeHoldersElement = {title: ""};

     // Build the pageable object
     $scope.searchPageable.build(searchEntity);

     $scope.searchPageable.showPage($stateParams.page === undefined ? 1 : $stateParams.page);
     console.log($scope.searchPageable);
 }

 $scope.createPageable();
 $scope.searchByName=function(searchContent){
     console.log(searchContent);
     $scope.createPageable();
 };

// Popular

$scope.status = status;

$scope.search=function(){
    $scope.createPageable(ConditionSearch);
}

if($scope.status === 1){
    $scope.search = $scope.searchWeeklyH;
}
if($scope.status === 2){
    $scope.search = $scope.searchMonthlyH;
}
if($scope.status === 3){
    $scope.search = $scope.searchYearlyH;
}
if($scope.status === 4){
    $scope.search = $scope.searchWeeklyC;
}
if($scope.status === 5){
    $scope.search = $scope.searchMonthlyC;
}
if($scope.status === 6){
    $scope.search = $scope.searchYearlyC;
}

$scope.searchWeeklyH = function(){
       $scope.createPageable(WeeklyHotSearch);
};

$scope.searchMonthlyH = function(){
      $scope.createPageable(MonthlyHotSearch);
};

$scope.searchYearlyH = function(){
    $scope.createPageable(YearlyHotSearch);
};
$scope.searchWeeklyC = function(){
    $scope.createPageable(WeeklyRecommendSearch);
};

$scope.searchMonthlyC = function(){
    $scope.createPageable(MonthlyRecommendSearch);
};
$scope.searchYearlyC = function(){
    $scope.createPageable(MonthlyRecommendSearch);
};


}]);
