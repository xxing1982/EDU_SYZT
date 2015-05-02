//readingCenterAddBookAdvancedSearchCtrl.jsc
ctrls.controller("objectiveQuestionController", ['$scope','$rootScope','$stateParams','Pageable',
        'ConditionSearch','QuickSearch','Book','config',function ($rootScope,$scope,$stateParams,Pageable,ConditionSearch,QuickSearch,config) {


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
            
    $scope.createPageable = function (){
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

if($stateParams.searchTerm!== ""){
     $scope.searchByName($stateParams.searchTerm);
     $scope.searchArguments.searchTerm = $stateParams.searchTerm;
}

$scope.imageServer = config.IMAGESERVER;

}]);