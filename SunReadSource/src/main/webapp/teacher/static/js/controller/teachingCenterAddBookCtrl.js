ctrls.controller("teachingCenterAddBookController", [
	 '$scope','$stateParams','config','LackFeedback'
	,'QuickSearch','ConditionSearch','Dictionary'
	,'WeeklyHotSearch','MonthlyHotSearch','YearlyHotSearch'
	,'WeeklyRecommendSearch','MonthlyRecommendSearch','YearlyRecommendSearch'
,function ($scope,$stateParams,config,LackFeedback
	,QuickSearch,ConditionSearch,Dictionary
	,WeeklyHotSearch,MonthlyHotSearch,YearlyHotSearch
	,WeeklyRecommendSearch,MonthlyRecommendSearch,YearlyRecommendSearch) {

		$scope.imageServer=config.IMAGESERVER; ;
		$scope.statuses_grade = new Array();
		$scope.statuses_category = new Array();
		$scope.selected_status = 0;

		$scope.addBook = '我要推荐';
		$scope.language = 1;

	//QuickSearch
	// $scope.quickSearch = addBookQuickSearch();

	if(typeof($scope.hots) === 'undefined'){
		addBookQuickSearch();
	}

	$scope.searchToAdvance=function(searchContent){
		if(searchContent!=""){
			createPageable(ConditionSearch);
			window.location.href="#teachingCenter/addBook/advanced/"+searchContent;
			// $scope.searchByName(searchContent);
			// $scope.searchArguments.searchTerm = searchContent;
		}
	}

	function addBookQuickSearch (){
		$scope.hots=QuickSearch.get({page:0,size:3,sortBy:"statistic.readNums"});
		$scope.recommends=QuickSearch.get({page:0,size:3,sortBy:"statistic.recommends"});
	}

	//Dictionary
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

		//refresh when AdvancedSearch
	if(typeof($scope.searchPageable === 'undefined'))
		createPageable(ConditionSearch);

		$scope.searchBooks = createPageable(ConditionSearch);

	 function createPageable (searchEntity){
	     $scope.searchPageable = new Pageable();

	     $scope.searchPageable.size = 4;
	     $scope.searchPageable.page = 1;

	     $scope.searchPageable.arguments=$scope.searchArguments;
	     // Set the startPage and length of number page array
// 	     console.log($scope.searchArguments);

	     $scope.searchPageable.pageNumbers.startPage = 1;
	     $scope.searchPageable.pageNumbers.content.length = 8;
	     // Set the placeholder elements
	     $scope.searchPageable.placeHolders.placeHoldersElement = {title: ""};

	     // Build the pageable object
	     $scope.searchPageable.build(searchEntity);

	     $scope.searchPageable.showPage($stateParams.page === undefined ? 1 : $stateParams.page);
	     console.log($scope.searchPageable);
	 }
	//
	//  $scope.searchByName=function(searchContent){
	//      console.log(searchContent);
	//      $scope.createPageable(ConditionSearch);
	//  };


// LackFeedback
    $scope.lackBookFeedback = function(){
        $scope.lackBook={
          author:$scope.author,
          isbn:$scope.isbn,
          name:$scope.name,
          publisher:$scope.publisher,
          language:$scope.language,
          publicationDate:$scope.publishDate
        }
        console.log($scope.lackBook);
        var added =  LackFeedback.save($scope.lackBook);
    }

		$scope.isbnValid = function(){
			var validIsbn = $scope.isbn;
			if(typeof(validIsbn) ==='undefined')
				return false;
			if(validIsbn.length ===10 || validIsbn.length === 13){
				return true;
			}
			else{
				return false;
			}
		};

		$scope.isRequired = function(model){
			if(typeof(model) ==='undefined')
				return false;
			else
				return true;
		}
}]);
