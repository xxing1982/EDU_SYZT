//mainCtrl.js
var ctrls = angular.module('nourControllers',['nourConfig', 'ngResource', 'userServices', 'noteServices', 'noteViewServices', 'noteTakeServices', 'paraServices', 'commentServices'
                                             ,'examServices', 'classServices', 'questionServices'
                                             ,'bookDetailServices','bookshelfServices','bookInShelfServices','addbookToShelfServices'
                                             ,'lackFeedbackServices','conditionSearchServices','quickSearchServices'
                                             ,'weeklyHotServices','monthlyHotServices'
                                             ,'weeklyRecommendServices','monthlyRecommendServices']);

ctrls.controller("mainController", ['$rootScope', '$scope', 'Student',"Bookshelf", "Note", "Class", "PassExam",
  function ($rootScope, $scope, Student,Bookshelf, Note, Class, PassExam) {
    //$rootScope.id = 2;
    //get userid
    $rootScope.id = sessionStorage.getItem("userId");


    //student info
    Student.get({id : $rootScope.id} ,function(data){
      $scope.userInfo = data;
      $rootScope.student = data;
      Class.get({id: $scope.userInfo.clazzId}, function(classData){
        $scope.userInfo.class=classData.name;
        $scope.userInfo.school=classData.compusName;
      });
    });    
    
    //bookshelf
    Bookshelf.get({id : $rootScope.id}, function(data){
      $scope.bookshelf = data;

        //todo
        //目标完成率
    })

    //testing
    PassExam.get($rootScope.id, function(data){
      $scope.exam = data;
    })

    //note
    Note.get({by: "users", id: $rootScope.id,  page: 0, size: 4, direction: "DESC", sortBy: "creationTime"}, 
      function(data){
        $scope.notes = data.content;
      })

    //hot note
    Note.get({page:0, size: 3, sortBy: 'commentCount', direction: 'DESC'}, function(data){
      $scope.hotNotes = data.content;
    })



  }]);
ctrls.filter('formatImg', function(){
  return function(input){
    if (input == undefined || input == "") {
      return "../static/img/picture.jpg";
    };
    return input;
  }
});

ctrls.filter('formatSize4', function(){
  return function(input){
    return input.substring(0, 4) + '...';
  }
});

ctrls.filter('formatSize6', function(){
  return function(input){
    return input.substring(0, 6) + '...';
  }
});

ctrls.filter('formatGender', function(){
 return function(input){
  if (input == 'male') {
    return "男生";
  };
  return "女生";
}
});