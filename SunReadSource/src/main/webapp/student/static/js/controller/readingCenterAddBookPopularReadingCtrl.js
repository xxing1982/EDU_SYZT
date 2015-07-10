//readingCenterAddBookPopularReadingCtrl.js

ctrls.controller("readingCenterAddBookPopularReadingController", ['$scope','$rootScope','$stateParams','Pageable','ConditionSearch','JoinShelf'
        ,'WeeklyHotSearch','MonthlyHotSearch','YearlyHotSearch','config','Dictionary',function ($scope,$rootScope,$stateParams,Pageable,ConditionSearch,JoinShelf
                                                         ,WeeklyHotSearch,MonthlyHotSearch,YearlyHotSearch,config,Dictionary) {
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


        $scope.statuses_grade = new Array();
        $scope.statuses_category = new Array();

      if(!$scope.statuses_grade.length > 0){
        Dictionary.query({type:"GRADE"},function(data){
          // console.log(grade);
          var temp;
          for(var i=0; i<data.length; i++){
            temp = {
              id:i,
              name:data[i].name,
              callback:function(){createPageable(ConditionSearch);}
            }
          //  console(typeof(temp));
            $scope.statuses_grade.push(temp);
          }
        console.log($scope.statuses_grade);
      })
      }

      if(!$scope.statuses_category.length > 0){
      Dictionary.query({type:"CATEGORY"},function(data){
        // console.log(grade);
        var temp;
        for(var i=0; i<data.length; i++){
          temp = {
            id:i,
            name:data[i].name,
            callback:function(){createPageable(ConditionSearch);}
          }
          $scope.statuses_category.push(temp);
        }
        console.log($scope.statuses_category);
      })
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
    if($scope.status === 3){
        $scope.search = $scope.searchYearly;
    }



	$scope.searchWeekly = function(){
         $scope.createPageable(WeeklyHotSearch);
	};

  $scope.searchMonthly = function(){
        $scope.createPageable(MonthlyHotSearch);
	};


  $scope.searchYearly = function(){
      $scope.createPageable(YearlyHotSearch);
  };

  $scope.addBooktoShelf = function(terms){
    var addBook = new JoinShelf();
    addBook.joinShelf(terms);
  } ;


    $scope.imageServer = config.IMAGESERVER;

}]);
