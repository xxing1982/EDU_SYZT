ctrls.controller("teachingCenterAddBookController", [
	 '$scope','$rootScope','$stateParams','config','LackFeedback'
	,'QuickSearch','ConditionSearch','Dictionary'
	,'WeeklyHotSearch','MonthlyHotSearch','YearlyHotSearch'
	,'WeeklyRecommendSearch','MonthlyRecommendSearch','YearlyRecommendSearch'
    ,'Tag','Booktag','LikeBook'
,function ($scope,$rootScope,$stateParams,config,LackFeedback
	,QuickSearch,ConditionSearch,Dictionary
	,WeeklyHotSearch,MonthlyHotSearch,YearlyHotSearch
	,WeeklyRecommendSearch,MonthlyRecommendSearch,YearlyRecommendSearch
    ,Tag,Booktag,LikeBook) {

		$scope.imageServer=config.IMAGESERVER;
		$scope.statuses_grade = new Array();
		$scope.statuses_category = new Array();
		$scope.selected_status = 0;

		$scope.addBook = '我要推荐';
		$scope.language = 1;

	//QuickSearc
	// $scope.quickSearch = addBookQuickSearch();

	if(typeof($scope.hots) === 'undefined'){
		addBookQuickSearch();
	}

	$scope.searchToAdvance=function(searchContent){
		if(searchContent!=""){
			createPageable(ConditionSearch);
			window.location.href="#teachingCenter/addBook/advanced/"+searchContent;
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

		if(typeof($scope.searchPageable) == 'undefined')
			createPageable(ConditionSearch);
		//refresh when AdvancedSearch

		$scope.searchBooks = function(){
			createPageable(ConditionSearch);
		};

	 function createPageable (searchEntity){
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

			$scope.isSearchSuccess = function(){
				if($scope.searchPageable.entities.content.length === 0)
					return false;
				else
					return true;
			}
	 }

	// Popular
  $scope.popularHot = function(){
		$scope.searchArguments.searchTerm = "";
	}
	$scope.popularRecommend = function(){
		$scope.searchArguments.searchTerm = "";
	}

	if($scope.statusH === 1){
	    $scope.searchBooks = $scope.searchWeeklyH;
	}
	if($scope.statusH === 2){
	    $scope.searchBooks = $scope.searchMonthlyH;
	}
	if($scope.statusH === 3){
	    $scope.searchBooks = $scope.searchYearlyH;
	}
	if($scope.statusR === 1){
	    $scope.searchBooks = $scope.searchWeeklyC;
	}
	if($scope.statusR === 2){
	    $scope.searchBooks = $scope.searchMonthlyC;
	}
	if($scope.statusR === 3){
	    $scope.searchBooks = $scope.searchYearlyC;
	}

	$scope.searchWeeklyH = function(){
	       createPageable(WeeklyHotSearch);
	};

	$scope.searchMonthlyH = function(){
	      createPageable(MonthlyHotSearch);
	};

	$scope.searchYearlyH = function(){
	    createPageable(YearlyHotSearch);
	};
	$scope.searchWeeklyC = function(){
	    createPageable(WeeklyRecommendSearch);
	};

	$scope.searchMonthlyC = function(){
	    createPageable(MonthlyRecommendSearch);
	};
	$scope.searchYearlyC = function(){
	    createPageable(MonthlyRecommendSearch);
	};

	$scope.iLike = function(){

	}


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
        // console.log($scope.lackBook);
				var added =  LackFeedback.save($scope.lackBook,function(data){
					$rootScope.modal ={
						title: "缺书反馈",
						content:"添加成功"
					};
					$('#alert-modal').modal();
					$rootScope.modal.click = function(){
							location.reload();
						}
					// console.log(data);
				},function(error){
					$rootScope.modal ={
						title: "缺书反馈",
						content:"反馈失败"+error.statusText
					};
					$('#alert-modal').modal();
					// console.log(error);
					$rootScope.modal.click = function(){
							location.reload();
						}
				});
    }

		$scope.isbn = '';

		$scope.isbnValid = function(){
			if($scope.isbn ==='')
				return true;
			if(typeof($scope.isbn) ==='undefined')
				return false;
			if($scope.isbn.length ===10 || $scope.isbn.length === 13)
				return true;
				
			return false;
		};

		$scope.isRequired = function(model){
			if(typeof(model) ==='undefined')
				return false;
			else
				return true;
		}


        // Bs dropdown statues
        $scope.statuses = { LESSON: [{id: 0, name: "不标记"}],
                            SUBJECT: [{id: 0, name: "不标记"}],
                            GRADE: [{id: 0, name: "不标记"}],
                            CHAPTER: [{id: 0, name: "不标记"}],
                            THEME: [{id: 0, name: "不标记"}] };
        $scope.selected_status = { LESSON: 0,
                                   SUBJECT: 0,
                                   GRADE: 0,
                                   CHAPTER: 0,
                                   THEME: 0 };

        // The tag object
        $scope.tags = Tag.get({}, function(){
            var tags = $scope.tags;
            for ( var i = 0; i < tags.length; i++ ){
                $scope.statuses[tags[i].type].push( tags[i] );
            }
        });

        // The booktag object
        $scope.booktag = {};

        $scope.booktag.render = function(book){
            $scope.booktag.name = book.name;
            $scope.booktag.book_id = book.id;
        };

        $scope.booktag.save = function(){

            // Calculate the count of target saved
            var target_saved = 0;
            for (var key in $scope.selected_status) {
                if ($scope.selected_status[key] !== 0) {
                    target_saved ++;
                }
            }

            // Save the changed booktag
            var saved = 0;
            for (var key in $scope.selected_status) {
                if ( $scope.selected_status[key] != 0 ) {
                    Booktag.save( { tag_id: $scope.selected_status[key],
                                    book_id: $scope.booktag.book_id }, function(){
                                        if ( saved + 1 === target_saved ){

                                            // Show the modal of success
                                            $rootScope.modal = {title: "标记书籍", content: "标记书籍成功！"};
                                            $('#alert-modal').modal();

                                            // Reset the selected_status
                                            for (var key in $scope.selected_status) {
                                               $scope.selected_status[key] = 0;
                                            }
                                        } else {
                                            saved ++;
                                        }

                                    });
                }
            }
        };

				$scope.isSelected = false;

				$scope.isSelect = function(para){
					// if ($scope.isSelected) {
					// 	$scope.isSelected = false;
					// }
						$scope.isSelected = true;
						// console.log(para.terms);
						// $("#"+para.terms.id).addClass('heartRed');
						$scope.likeBook = new LikeBook();
						$scope.likeBook.$update({id:para.terms.id,userId:$rootScope.id});
				}


}]);
