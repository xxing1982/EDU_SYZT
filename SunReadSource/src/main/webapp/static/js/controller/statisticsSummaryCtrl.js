//statisticsSummary.js

ctrls.controller("statisticsSummaryController", ['$scope' , function ($scope) {
    
    $scope.statuses = [{
        id: 1,
        name: "Low"        
    }, {
        id: 2,
        name: "Normal"        
    }, {
        id: 3,
        name: "High"        
    }, {
        id: 4,
        name: "Urgent"        
    }, {
        id: 5,
        name: "Immediate"        
    }];
    $scope.selected_status = 3;
}]);