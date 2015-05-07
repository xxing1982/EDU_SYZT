ctrls.controller("teachingCenterAddBookSearchController", ['$scope','QuickSearch'
        // ,'ConditionSearch','WeeklyHotSearch','MonthlyHotSearch','WeeklyRecommendSearch','MonthlyRecommendSearch',
        ,function ($scope,QuickSearch){
        $scope.imageServer="http://images.sunreading.cn" ;

    // $scope.addBook = {
    //     addBook: '我要推荐',
    //     QuickSearch:'快速查找',
    //     AdvanceSearch:'全部的书',
    //     PopularReading:'热门阅读',
    //     PopularRecommend:'热门推荐'
    // }

    //
    // $scope.searchContent = "";
    //
    // var pageSize = 4;
    // var searchTerm='isbn';
    // var status = 0;
    //
    // var level=0;
    // var category=0;
    // var testType=0;
    // var literature=0;
    // var grade=0;
    // var category=0;
    // var language=0;
    // var resource=0;
    // var pointRange=0;
    //
    // $scope.level = level;
    // $scope.category = category;
    // $scope.testType = testType;
    // $scope.literature = literature;
    // $scope.grade = grade;
    // $scope.category = category;
    // $scope.language = language;
    // $scope.resource = resource;
    // $scope.pointRange = pointRange;
    // $scope.statuses_grade = [{
    //     id: 0,
    //     name:"全部年级",
    //     callback: function(){$scope.search()}
    // }, {
    //     id: 1,
    //     name: "1年级",
    //     callback: function(){$scope.search()}
    // }, {
    //     id: 2,
    //     name: "2年级",
    //     callback: function(){$scope.search()}
    // }, {
    //     id: 3,
    //     name: "3年级",
    //     callback: function(){$scope.search()}
    // }, {
    //     id: 4,
    //     name: "4年级",
    //     callback: function(){$scope.search()}
    // }, {
    //     id: 5,
    //     name: "5年级",
    //     callback: function(){$scope.search()}
    // }];
    //
    // $scope.statuses_category = [{
    //     id: 0,
    //     name:"全部类型",
    //     callback: function(){$scope.search()}
    // }, {
    //     id: 1,
    //     name: "类型一",
    //     callback: function(){$scope.search()}
    // }, {
    //     id: 2,
    //     name: "类型二",
    //     callback: function(){$scope.search()}
    // }, {
    //      id: 3,
    //     name: "类型三",
    //     callback: function(){$scope.search()}
    // }, {
    //     id: 4,
    //     name: "类型四",
    //     callback: function(){$scope.search()}
    // }, {
    //     id: 5,
    //     name: "类型五",
    //     callback: function(){$scope.search()}
    // }];
    // $scope.selected_status = 0;
    // $scope.status = status;

    $scope.hots=QuickSearch.get({page:0,size:3,sortBy:"statistic.readNums"},function(){
      console.log($scope.hots);
    })
    //
    $scope.recommends=QuickSearch.get({page:0,size:3,sortBy:"statistic.recommends"},function(){
      console.log($scope.hots);
    })
  //
  //   $scope.addBookSearch=ConditionSearch.get({page:0,size:pageSize,level:level,category:category
  //                                               ,testType:testType,literature:literature,category:category
  //                                               ,grade:grade,language:language,resource:resource,pointRange:pointRange}
  //   ,function(){
  //       console.log($scope.advancedSearch)
  //   });
  //
  //   $scope.searchByName=function(){
  //       $scope.addBookSearch=QuickSearch.get({page:0,size:pageSize,searchTerm:$scope.searchContent}
  //                                            ,function(){
  //           console.log($scope.advancedSearch);
  //           console.log($scope.searchContent);
  //       })
  //
  //   };
  //
	// $scope.search = function(){
  //       console.log();
  //       console.log($scope.testType)
  //       console.log($scope.grade);
  //       $scope.addBookSearch=ConditionSearch.get({page:0,size:pageSize,level:$scope.level,category:$scope.category
  //                                                   ,testType:$scope.testType,literature:$scope.literature,category:$scope.category
  //                                                   ,grade:$scope.grade,language:$scope.language,resource:$scope.resource,pointRange:$scope.pointRange},function(){
  //           console.log($scope.advancedSearch)
  //       });
	// };
  //
  //
	// $scope.searchWeeklyH = function(){
  //       console.log(grade);
  //       console.log($scope.testType)
  //       console.log($scope.grade);
  //       $scope.addBookSearch=WeeklyHotSearch.get({page:$scope.page,size:pageSize,level:$scope.level,category:$scope.category
  //                                               ,testType:$scope.testType,literature:$scope.literature,category:$scope.category
  //                                               ,grade:$scope.grade,language:$scope.language,resource:$scope.resource,pointRange:pointRange},function(){
  //           console.log($scope.addBookSearch)
  //       });
	// };
  //
  //   $scope.searchMonthlyH = function(){
  //       console.log(grade);
  //       console.log($scope.testType)
  //       console.log($scope.grade);
  //       $scope.addBookSearch=MonthlyHotSearch.get({page:$scope.page,size:pageSize,level:$scope.level,category:$scope.category
  //                                               ,testType:$scope.testType,literature:$scope.literature,category:$scope.category
  //                                               ,grade:$scope.grade,language:$scope.language,resource:$scope.resource,pointRange:pointRange},function(){
  //           console.log($scope.addBookSearch)
  //       });
	// };
  //
  //   $scope.searchWeeklyR = function(){
  //       console.log(grade);
  //       console.log($scope.testType)
  //       console.log($scope.grade);
  //       $scope.addBookSearch=WeeklyRecommendSearch.get({page:0,size:pageSize,level:$scope.level,category:$scope.category
  //                                               ,testType:$scope.testType,literature:$scope.literature,category:$scope.category
  //                                               ,grade:$scope.grade,language:$scope.language,resource:$scope.resource,pointRange:pointRange},function(){
  //           console.log($scope.recommendSearch)
  //       });
	// };
  //
  //   $scope.searchMonthlyR = function(){
  //       console.log(grade);
  //       console.log($scope.testType)
  //       console.log($scope.grade);
  //       $scope.addBookSearch=MonthlyRecommendSearch.get({page:0,size:pageSize,level:$scope.level,category:$scope.category
  //                                               ,testType:$scope.testType,literature:$scope.literature,category:$scope.category
  //                                               ,grade:$scope.grade,language:$scope.language,resource:$scope.resource,pointRange:pointRange},function(){
  //           console.log($scope.recommendSearch)
  //       });
  //   };
  //
  //   $scope.addBooktoShelf = function(terms){
  //       console.log(terms);
  //       var bookId = terms.id;
  //       var bookInShelf = {
  //           bookAttribute: true,
  //           readState: false
  //           }
  //       console.log(bookInShelf);
  //       AddbookToShelf.save({bookshelfId:1,bookId:bookId},bookInShelf);
        //
        //   var futureResponse = $http.get('date.json');
        //       futureResponse.error(function(date,status,headers,config){
        //         throw new Error('Something went wrong...');
        //       })
        // };

}]);
