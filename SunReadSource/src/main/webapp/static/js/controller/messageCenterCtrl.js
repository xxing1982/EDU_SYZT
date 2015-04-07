//messageCenter.js

ctrls.controller("messageCenterController", ['$scope', function ($scope) {
    // Initlizate the dropdown statues
    $scope.statuses = [{
        id: 1,
        name: "一年级上学期"        
    }, {
        id: 2,
        name: "一年级下学期"        
    }, {
        id: 3,
        name: "二年级上学期"        
    }, {
        id: 4,
        name: "二年级下学期"        
    }, {
        id: 5,
        name: "三年级上学期"        
    }, {
        id: 6,
        name: "三年级下学期"        
    }];
    
    // Choose the stutus by script 
    // Once the dropdown option changed
    // selected_status will changed simultaneity
    $scope.selected_status = 3;
}]);