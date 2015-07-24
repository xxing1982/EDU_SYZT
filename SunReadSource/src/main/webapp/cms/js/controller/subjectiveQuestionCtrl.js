//readingCenterAddBookAdvancedSearchCtrl.jsc
ctrls.controller("subjectiveQuestionCtrl", ['$scope','$rootScope','$stateParams','Pageable','config','GetSubjectivequestions', 'Subjectivequestions', 
    function ($scope,$rootScope,$stateParams,Pageable,config,GetSubjectivequestions,Subjectivequestions) {

            
    $scope.searchArguments = {
        searchTerm:""
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

    $scope.updateSys = function(item){
        $scope.edit = angular.copy(item);
    }
    $scope.EditSys = function(){
        Subjectivequestions.Update($scope.edit, function(){
            $("#editModal").modal('hide');
        })
    }
}]);