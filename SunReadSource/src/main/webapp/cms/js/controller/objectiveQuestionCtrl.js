//readingCenterAddBookAdvancedSearchCtrl.jsc
ctrls.controller("objectiveQuestionCtrl", ['$scope','$rootScope','$stateParams','Pageable','config','GetObjectivequestions', 'Objectivequestions', 'Option',
    function ($scope,$rootScope,$stateParams,Pageable,config,GetObjectivequestions,Objectivequestions,Option) {

            
    $scope.searchArguments = {
        topic:"",
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
        $scope.searchPageable.build(GetObjectivequestions);
        
        $scope.searchPageable.showPage($stateParams.page === undefined ? 1 : $stateParams.page);
    }
    $scope.createPageable();

    $scope.ShowOption = function(item){
        $scope.selectQuestion = item.options;
    }
    $scope.updateSys = function(item){
        $scope.edit = angular.copy(item);
    }
    $scope.EditSys = function(){
        Objectivequestions.Update($scope.edit, function(){
            $("#editModal").modal('hide');
        })
    }


    $scope.updateOption = function(item){
        $scope.editOption = angular.copy(item);
    }
    $scope.EditOption = function(){
        Option.Update($scope.edit, function(){
            $("#editOptionModal").modal('hide');
        })
    }
}]);

ctrls.filter('formatType', function () {
    return function (data) {
        if (data == "VERIFY") {
            return "验证";
        }
        else if (data == "WORD") {
            return "词汇";
        }


        return data;
    };
});