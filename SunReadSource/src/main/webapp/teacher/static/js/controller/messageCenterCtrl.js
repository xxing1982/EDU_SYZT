//messageCenter.js
ctrls.controller("messageCenterController", ['$rootScope','$scope','$stateParams'
                                        ,'Teacher','Hotreader','SendMessages','GetMessages','DeleteMessages','TeachersFromCampus'
                                      ,function ($rootScope,$scope,$stateParams
                                        ,Teacher,Hotreader,SendMessages,GetMessages,DeleteMessages,TeachersFromCampus) {

    // $scope.test = function(){alert("test")};

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
    $scope.teacher = Teacher.get({id:$rootScope.id},function(){
      //list for teachers
      var myTeachers = TeachersFromCampus.get({campusId:$scope.teacher.campusId,page:0,size:100},function(){
        $scope.myTeachers = myTeachers.content;
        //Initialization
        $scope.searchTextTeacher = '';
        $scope.chooseTeachers=function(teacher){
          $scope.searchTextStudent ='';
          console.log(teacher);
          $scope.searchTextTeacher = teacher.username;
          $scope.recieveUserId = teacher.id;
        };
      });
      // console.log($scope.student.campusId);
      //list for students
      var classmates = Hotreader.get({by:'campus',id:$scope.teacher.campusId,sortBy:"point",page:0,size:100},function(){
        // console.log(classmates.content);
        $scope.classmates = classmates.content;
        $scope.searchTextStudent ='';
        $scope.chooseStudent=function(student){
          $scope.searchTextTeacher = '';
          console.log(student);
          $scope.searchTextStudent = student.username;
          $scope.recieveUserId = student.id;
        };
      });
    });

    $scope.text = {
      message:""
    };


    //recieve user cant be none
    // no one = false;
    //at least one = true;
    var isHaveRecievedUser = function(){
      if($scope.searchTextStudent ==='' && $scope.searchTextTeacher === '')
        return false;
      else return true;
     }

    $scope.sendMessage = function(){
      console.log($scope.recieveUserId);
      if($scope.recieveUserId != undefined && $scope.message != ""&&isHaveRecievedUser){
        console.log($scope.text);
        SendMessages.save({sendUserId:$rootScope.id,recieveUserId:$scope.recieveUserId},$scope.text,function(){
          $scope.searchTextStudent = "";
          $scope.searchTextTeacher = '';
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

  $scope.show=function(content){
      $rootScope.modal = {title: "消息", content: content};
      $('#alert-modal').modal();
  }
}]);

ctrls.filter('messageFormatSize', function () {
    return function (input) {
        if (input.length > 70)
            return input.substring(0, 70) + '...';
        else
            return input;
    }
});
