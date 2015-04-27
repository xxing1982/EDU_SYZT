//messageCenter.js

ctrls.controller("messageCenterController", ['$rootScope','$scope','$stateParams','Pageable','SendMessages','GetMessages',
                                    function ($rootScope,$scope,$stateParams,Pageable,SendMessages,GetMessages) {

    // $scope.test = function(){alert("test")};

    // Initlizate the dropdown statues
    $scope.statuses = [{
        id: 1
    }, {
        id: 2,
        name: "老校长",
        callback: $scope.test
    }, {
        id: 3,
        name: "副校长",
        callback: $scope.test
    }, {
        id: 4,
        name: "三校长",
        callback: $scope.test
    }, {
        id: 5,
        name: "四校长"
    }, {
        id: 6,
        name: "主任"
    }];

    var page = 0;
    var size = 10;
    var recieve = 'to';
    var deliver = 'from';
    var recievedArguments = {
      source:recieve,
      id:$rootScope.id
    }
    var deliveredArguments = {
      source:deliver,
      id:$rootScope.id
    }


    // Choose the stutus by script
    // Once the dropdown option changed
    // selected_status will changed simultaneity
    $scope.selected_status = 1;

    $scope.recievedMessages = GetMessages.get({source:recieve,id:$rootScope.id,page:page,size:size}
        ,function(){
          console.log($scope.recievedMessages);
        })

    var createRecievedPageable = function(){
      $scope.recievedPageable = new Pageable();

      $scope.recievedPageable.size = size;
      $scope.recievedPageable.page = page;

      $scope.recievedPageable.arguments = recievedArguments;
      // Set the startPage and length of number page array
      console.log(recievedArguments);

      $scope.recievedPageable.pageNumbers.startPage = 1;
      $scope.recievedPageable.pageNumbers.content.length = 8;
      // Set the placeholder elements
      $scope.recievedPageable.placeHolders.placeHoldersElement = {title: ""};

      // Build the pageable object
      $scope.recievedPageable.build(GetMessages);

      $scope.recievedPageable.showPage($stateParams.page === undefined ? 1 : $stateParams.page);
      console.log($scope.recievedPageable);
  }
  var createDeliveredPageable = function(){
    $scope.deliveredPageable = new Pageable();

    $scope.deliveredPageable.size = size;
    $scope.deliveredPageable.page = page;

    $scope.deliveredPageable.arguments = deliveredArguments;
    // Set the startPage and length of number page array
    console.log(deliveredArguments);

    $scope.deliveredPageable.pageNumbers.startPage = 1;
    $scope.deliveredPageable.pageNumbers.content.length = 8;
    // Set the placeholder elements
    $scope.deliveredPageable.placeHolders.placeHoldersElement = {title: ""};

    // Build the pageable object
    $scope.deliveredPageable.build(GetMessages);

    $scope.deliveredPageable.showPage($stateParams.page === undefined ? 1 : $stateParams.page);
    console.log($scope.deliveredPageable);
}

    createDeliveredPageable();
    createRecievedPageable();

}]);
