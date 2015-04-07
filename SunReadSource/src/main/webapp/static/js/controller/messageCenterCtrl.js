//messageCenter.js

ctrls.controller("messageCenterController", ['$scope', function ($scope) {
    // Initlizate the dropdown statues
    $scope.statuses = [{
        id: 1       
    }, {
        id: 2,
        name: "老校长"        
    }, {
        id: 3,
        name: "二校长"        
    }, {
        id: 4,
        name: "三校长"        
    }, {
        id: 5,
        name: "斯校长"        
    }, {
        id: 6,
        name: "三学期"        
    }];
    
    // Choose the stutus by script 
    // Once the dropdown option changed
    // selected_status will changed simultaneity
    $scope.selected_status = 1;
}]);