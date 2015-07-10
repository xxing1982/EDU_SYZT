//readingCenterAddBookAdvancedSearchCtrl.jsc
ctrls.controller("readingCenterAddBookAdvancedSearchController", ['$scope','$rootScope','$stateParams','Pageable',
        'ConditionSearch','QuickSearch','JoinShelf','config','Dictionary',function ($rootScope,$scope,$stateParams,Pageable,ConditionSearch,QuickSearch,JoinShelf,config,Dictionary) {


//    $scope.searchContent="";

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

    $scope.createPageable = function (){
        $scope.searchPageable = new Pageable();

        $scope.searchPageable.size = 4;
        $scope.searchPageable.page = 1;

        $scope.searchPageable.arguments=$scope.searchArguments;
        // Set the startPage and length of number page array
        // console.log($scope.searchArguments);

        $scope.searchPageable.pageNumbers.startPage = 1;
        $scope.searchPageable.pageNumbers.content.length = 8;
        // Set the placeholder elements
        $scope.searchPageable.placeHolders.placeHoldersElement = {title: ""};

        // Build the pageable object
        $scope.searchPageable.build(ConditionSearch);

        $scope.searchPageable.showPage($stateParams.page === undefined ? 1 : $stateParams.page);
        console.log($scope.searchPageable);
    }

    $scope.createPageable();


    $scope.searchByName=function(searchContent){
        console.log(searchContent);
        $scope.createPageable();
    };

	$scope.search = function(searchArguments){
        $scope.createPageable();
	};

  $scope.addBooktoShelf = function(terms){
    var addBook = new JoinShelf();
    addBook.joinShelf(terms);
  } ;


if($stateParams.searchTerm!== ""){
     $scope.searchByName($stateParams.searchTerm);
     $scope.searchArguments.searchTerm = $stateParams.searchTerm;
}
$("#advanceSearchTab div")[0].click();
$scope.imageServer = config.IMAGESERVER;

}]);
