ctrls.controller("teachingCenterAddBookSearchController", ['$scope','$stateParams','config','QuickSearch','ConditionSearch','Dictionary'
        ,'WeeklyHotSearch','MonthlyHotSearch','YearlyHotSearch','WeeklyRecommendSearch','MonthlyRecommendSearch','YearlyRecommendSearch'
        ,function ($scope,$stateParams,config,QuickSearch,ConditionSearch,Dictionary
          ,WeeklyHotSearch,MonthlyHotSearch,YearlyHotSearch,WeeklyRecommendSearch,MonthlyRecommendSearch,YearlyRecommendSearch){

        $scope.imageServer=config.IMAGESERVER; ;
        $scope.statuses_grade = new Array();
        $scope.statuses_category = new Array();
        $scope.selected_status = 0;

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

 // $scope.createPageable(ConditionSearch);

 $scope.searchBooks = function(){
     $scope.createPageable(ConditionSearch);
 };

 $scope.searchBooks();

 $scope.searchByName=function(searchContent){
     console.log(searchContent);
     $scope.createPageable(ConditionSearch);
 };

 //Dictionary
 Dictionary.query({type:"GRADE"},function(data){
   // console.log(grade);
   var temp;
   for(var i=0; i<data.length; i++){
     temp = {
       id:i,
       name:data[i].name,
       callback:function(){$scope.searchBooks()}
     }
    //  console(typeof(temp));
     $scope.statuses_grade.push(temp);
   }
   console.log($scope.statuses_grade);
 })

 Dictionary.query({type:"CATEGORY"},function(data){
   // console.log(grade);
   var temp;
   for(var i=0; i<data.length; i++){
     temp = {
       id:i,
       name:data[i].name,
       callback:function(){$scope.searchBooks()}
     }
     $scope.statuses_category.push(temp);
   }
   console.log($scope.statuses_category);
 })

// Popular

$scope.statusH = 1;
$scope.statusR = 1;

if($scope.statusH === 1){
    $scope.search = $scope.searchWeeklyH;
}
if($scope.statusH === 2){
    $scope.search = $scope.searchMonthlyH;
}
if($scope.statusH === 3){
    $scope.search = $scope.searchYearlyH;
}
if($scope.statusR === 1){
    $scope.search = $scope.searchWeeklyC;
}
if($scope.statusR === 2){
    $scope.search = $scope.searchMonthlyC;
}
if($scope.statusR === 3){
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
