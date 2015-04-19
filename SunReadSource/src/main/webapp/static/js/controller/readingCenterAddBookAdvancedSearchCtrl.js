//readingCenterAddBookAdvancedSearchCtrl.jsc
ctrls.controller("readingCenterAddBookAdvancedSearchController", ['$scope','$rootScope','$stateParams','Pageable',
        'ConditionSearch','QuickSearch','AddbookToShelf',function ($rootScope,$scope,$stateParams,Pageable,ConditionSearch,QuickSearch,AddbookToShelf) {


    var searchContent="";

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
    $scope.searchContent = searchContent;
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


    $scope.advancedSearch=ConditionSearch.get({page:0,size:pageSize,level:level,category:category
                                                ,testType:testType,literature:literature,category:category
                                                ,grade:grade,language:language,resource:resource,pointRange:pointRange}
    ,function(){
        console.log($scope.advancedSearch)
    });


    $scope.searchPageable = new Pageable();

    $scope.searchPageable.size = 10;
    $scope.searchPageable.page = 1;

    $scope.searchPageable.arguments={level:$scope.level,category:$scope.category
                                                ,testType:$scope.testType,literature:$scope.literature,category:$scope.category
                                                ,grade:$scope.grade,language:$scope.language,resource:$scope.resource,pointRange:$scope.pointRange};
    // Set the startPage and length of number page array
    $scope.searchPageable.pageNumbers.startPage = 1;
    $scope.searchPageable.pageNumbers.content.length = 8;

    // Build the pageable object
    $scope.searchPageable.build(ConditionSearch);
    $scope.searchPageable.showPage($stateParams.page === undefined ? 1 : $stateParams.page);



    $scope.searchByName=function(searchContent){
        console.log(searchContent);
        $scope.advancedSearch=QuickSearch.get({page:0,size:pageSize,searchTerm:searchContent}
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
            console.log($scope.advancedSearch);
            console.log($scope.searchPageable);
        });
	};

    $scope.addBooktoShelf = function(terms){
        console.log(terms);
        var bookId = terms.id;
        var bookInShelf = {
            bookAttribute: false,
            readState: false
            }
        console.log(XMLHttpRequest.state);
        try{
             AddbookToShelf.save({bookshelfId:$rootScope.id,bookId:bookId},bookInShelf)
             alert("添加成功");
             //throw new Error("添加失败");
        }
        catch(e){

            alert(e);
        }
    };



}]);
