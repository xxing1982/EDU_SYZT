//messageCenter.js

ctrls.controller("messageCenterController", ['$scope','SendMessages','GetMessages',
                                    function ($scope,SendMessages,GetMessages) {

    $scope.test = function(){alert("test")};

    // Initlizate the dropdown statues
    $scope.statuses = [{
        id: 1
    }, {
        id: 2,
        name: "老校长",
        callback: $scope.test
    }, {
        id: 3,
        name: "二校长",
        callback: $scope.test
    }, {
        id: 4,
        name: "三校长",
        callback: $scope.test
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
