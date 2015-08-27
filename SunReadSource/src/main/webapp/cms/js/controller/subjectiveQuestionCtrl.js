//readingCenterAddBookAdvancedSearchCtrl.jsc
ctrls.controller("subjectiveQuestionCtrl", ['$scope','$rootScope','$stateParams','Pageable','config','GetSubjectivequestions', 'Subjectivequestions', 
    function ($scope,$rootScope,$stateParams,Pageable,config,GetSubjectivequestions,Subjectivequestions) {

            
    $scope.searchArguments = {
        topic:""
    }

    $scope.searchByName = function(){
        $scope.createPageable();
    }
    
    $scope.createPageable = function (){
        $scope.searchPageable = new Pageable();

        $scope.searchPageable.size = 4;
        $scope.searchPageable.page = 1;

        $scope.searchPageable.arguments=$scope.searchArguments;
        // Set the startPage and length of number page array
        //console.log($scope.searchArguments);
        
        $scope.searchPageable.pageNumbers.startPage = 1;
        $scope.searchPageable.pageNumbers.content.length = 8;
        // Set the placeholder elements
        $scope.searchPageable.placeHolders.placeHoldersElement = {title: ""};

        // Build the pageable object
        $scope.searchPageable.build(GetSubjectivequestions);
        
        $scope.searchPageable.showPage($stateParams.page === undefined ? 1 : $stateParams.page);
    }
    $scope.createPageable();


    $scope.edit = {};
    $scope.updateSys = function(item){
        var temp = angular.copy(item);
        $scope.edit.id = temp.id;
        $scope.edit.topic = temp.topic;
        $scope.edit.bookId = temp.bookId;
        $scope.edit.questionType = temp.questionType;
    }
    $scope.EditSys = function(){
        Subjectivequestions.Update($scope.edit, function(){
            $("#editModal").modal('hide');
            location.reload();
        })
    }
}]);

ctrls.filter('formatType1', function () {
    return function (data) {
        if (data == "FIRST") {
            return "验证";
        }
        else if (data == "SECOND") {
            return "词汇";
        }


        return data;
    };
});