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

    $scope.edit = {};
    $scope.updateSys = function(item){
        var temp = angular.copy(item);
        $scope.edit.id = temp.id;
        $scope.edit.topic = temp.topic;
        $scope.edit.bookId = temp.bookId;
        $scope.edit.objectiveType = temp.objectiveType;
        $scope.edit.correctAnswer = temp.correctAnswer;
        delete $scope.edit.correctAnswer.creationTime;
        $scope.edit.options = temp.options;
        for(var i = 0; i < temp.options.length; i++){
            delete $scope.edit.options[i].creationTime;
        }
    }
    $scope.EditSys = function(){
        Objectivequestions.Update($scope.edit, function(){
            $("#editModal").modal('hide');
            location.reload();
        })
    }


    $scope.updateOption = function(item){
        $scope.editOption = angular.copy(item);
    }
    $scope.EditOption = function(){
        Option.Update($scope.editOption, function(){
            $("#editOptionModal").modal('hide');
            location.reload();
        })
    }
}]);

ctrls.filter('formatType', function () {
    return function (data) {
        if (data == "VERIFY") {
            return "验证题";
        }
        else if (data == "WORD") {
            return "词汇题";
        }


        return data;
    };
});