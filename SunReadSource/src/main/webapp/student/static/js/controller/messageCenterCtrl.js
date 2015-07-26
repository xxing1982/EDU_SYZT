//messageCenter.js

ctrls.controller("messageCenterController", ['$rootScope','$scope','$stateParams','Pageable'
                                        ,'Student','Hotreader','SendMessages','GetMessages','DeleteMessages','TeachersFromCampus'
                                      ,function ($rootScope,$scope,$stateParams,Pageable
                                        ,Student,Hotreader,SendMessages,GetMessages,DeleteMessages,TeachersFromCampus) {

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
    var size = 8;
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

    // $scope.recievedMessages = GetMessages.get({source:recieve,id:$rootScope.id,page:page,size:size}
    //     ,function(){
    //       console.log($scope.recievedMessages);
    //     })

    $scope.createRecievedPageable = function(){
      $scope.recievedPageable = new Pageable();

      $scope.recievedPageable.size = size;
      $scope.recievedPageable.page = page;

      $scope.recievedPageable.arguments = recievedArguments;
      // Set the startPage and length of number page array
      // console.log(recievedArguments);

      $scope.recievedPageable.pageNumbers.startPage = 1;
      $scope.recievedPageable.pageNumbers.content.length = 8;
      // Set the placeholder elements
      $scope.recievedPageable.placeHolders.placeHoldersElement = {title: ""};

      // Build the pageable object
      $scope.recievedPageable.build(GetMessages);

      $scope.recievedPageable.showPage($stateParams.page === undefined ? 1 : $stateParams.page);
      console.log($scope.recievedPageable);
  }
  $scope.createDeliveredPageable = function(){
    $scope.deliveredPageable = new Pageable();

    $scope.deliveredPageable.size = size;
    $scope.deliveredPageable.page = page;

    $scope.deliveredPageable.arguments = deliveredArguments;
    // Set the startPage and length of number page array
    // console.log(deliveredArguments);

    $scope.deliveredPageable.pageNumbers.startPage = 1;
    $scope.deliveredPageable.pageNumbers.content.length = 8;
    // Set the placeholder elements
    $scope.deliveredPageable.placeHolders.placeHoldersElement = {title: ""};

    // Build the pageable object
    $scope.deliveredPageable.build(GetMessages);

    $scope.deliveredPageable.showPage($stateParams.page === undefined ? 1 : $stateParams.page);
    console.log($scope.deliveredPageable);
}

$scope.createDeliveredPageable();
$scope.createRecievedPageable();

    $scope.recieveUserId = $rootScope.id;
    $scope.student = Student.get({id:$rootScope.id},function(){
      //list for teachers
      var myTeachers = TeachersFromCampus.get({campusId:$scope.student.campusId,page:0,size:100},function(){
        $scope.myTeachers = myTeachers.content;
        //Initialization
        $scope.searchTextTeacher = '';
        $scope.chooseTeachers=function(teacher){
          console.log(teacher);
          $scope.searchTextTeacher = teacher.username;
          $scope.recieveUserId = teacher.id;
        };

      });
      // console.log($scope.student.campusId);
      //list for students
      var classmates = Hotreader.get({by:'campus',id:$scope.student.campusId,sortBy:"point",page:0,size:100},function(){
        // console.log(classmates.content);
        $scope.classmates = classmates.content;
        $scope.searchTextStudent ='';
        $scope.chooseStudent=function(student){
          console.log(student);
          $scope.searchTextStudent = student.username;
          $scope.recieveUserId = student.id;
        };
      });


    });
    $scope.text = {
      message:""
    };

    $scope.sendMessage = function(){
      console.log($scope.recieveUserId);
      if($scope.recieveUserId != undefined && $scope.message != ""&&$scope.searchTextStudent!=''){
        console.log($scope.text);
        SendMessages.save({sendUserId:$rootScope.id,recieveUserId:$scope.recieveUserId},$scope.text,function(){
          //empty string after the message sended
          $scope.searchTextStudent = "";
          $rootScope.modal ={
              title: "发送成功",
              content:$scope.text.message
            };
          $('#alert-modal').modal();
          $rootScope.modal.click = function(){
              location.reload();
              }
        },function(e){
          $rootScope.modal ={
              title: "",
              content:"发送失败"
            };
          $('#alert-modal').modal();
        })
      }
      else{
        $rootScope.modal ={
            title: "",
            content:"发送失败"
          };
        $('#alert-modal').modal();
        }
    }

    $scope.deleteMessage = function(message){
      console.log(message);
      DeleteMessages.delete({id:message.id},function(){
        $rootScope.modal ={
            title: "删除成功",
            content:$scope.text.message
          };
        $('#alert-modal').modal();
        $rootScope.modal.click = function(){
            location.reload();
            }
      },function(e){
        $rootScope.modal ={
            title: "",
            content:"删除失败"
          };
        $('#alert-modal').modal();
    });
  };
}]);
