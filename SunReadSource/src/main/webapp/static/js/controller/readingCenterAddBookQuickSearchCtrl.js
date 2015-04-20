//readingCenterAddBookQuickSearchCtrl.js
ctrls.controller("readingCenterAddBookQuickSearchController", ['$rootScope','$scope','AddbookToShelf','QuickSearch','config',function ($rootScope,$scope,AddbookToShelf,QuickSearch,config) {
	$scope.name='阅读中心->添加书籍->快速查找';

    var pageSize = 3;
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

    // $scope.hots=WeeklyHotSearch.get({page:0,size:3,level:$scope.level,category:$scope.category
    //                                             ,testType:$scope.testType,literature:$scope.literature,category:$scope.category
    //                                             ,grade:$scope.grade,language:$scope.language,resource:$scope.resource,pointRange:pointRange},function(){
    //         console.log($scope.popularSearch)
    //     });
		//
    // $scope.recommends=WeeklyRecommendSearch.get({page:0,size:3,level:$scope.level,category:$scope.category
    //                                             ,testType:$scope.testType,literature:$scope.literature,category:$scope.category
    //                                             ,grade:$scope.grade,language:$scope.language,resource:$scope.resource,pointRange:pointRange},function(){
    //         console.log($scope.recommendSearch)
    //     });


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

    $scope.searchToAdvance=function(searchContent){
        window.location.href="#readingCenter/addBook/advanced/"+searchContent;
    }
$scope.imageServer = config.IMAGESERVER;
}]);
