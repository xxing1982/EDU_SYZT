//mainCtrl.js
var ctrls = angular.module('nourControllers',['nourConfig', 'ngResource', 'userServices', 'noteServices', 'paraServices', 'commentServices'
                                             ,'examServices'
                                             ,'bookDetailServices','bookshelfServices','bookInShelfServices','addbookToShelfServices'
                                             ,'lackFeedbackServices','conditionSearchServices','quickSearchServices'
                                             ,'weeklyHotServices','monthlyHotServices'
                                             ,'weeklyRecommendServices','monthlyRecommendServices']);

ctrls.controller("mainController", ['$rootScope', '$scope', 'Student',"Bookshelf", "Note", 
  function ($rootScope, $scope, Student,Bookshelf, Note) {
    $rootScope.id = 2;


    //student info
    Student.get({id : $rootScope.id} ,function(data){
      $scope.userInfo = data;
      $rootScope.student = data;
    });    
    
    //bookshelf
    Bookshelf.get({id : $rootScope.id}, function(data){
      $scope.bookshelf = data;

        //todo
        //目标完成率
      })

    //note
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