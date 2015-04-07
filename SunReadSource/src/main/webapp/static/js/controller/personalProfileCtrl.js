//personalProfile.js

ctrls.controller("personalProfileController", ['$rootScope', '$scope', function ($rootScope, $scope) {
    
    // Basic information
    $scope.basicInformation = new Object();
    $scope.basicInformation.content = { name:  $rootScope.student.username,
                                        gender: $rootScope.student.gender,
                                        birthday: $rootScope.student.birthday,
                                        school: '学校',
                                        clazz: '班级' };
    
    // Personal information
    $scope.personalInformation = new Object();
    $scope.personalInformation.content = { nickname:  $rootScope.student.nickname,
                                           qq: "12345",
                                           wechat: "wechat",
                                           email: $rootScope.student.email,
                                           phone: $rootScope.student.phoneNumber };
    $scope.personalInformation.editable = false;
    
    
    // Invoke this with the entity object
    $scope.toggleEdit = function(editObj, save){
        
        if (save) {
            for (var key in editObj.cached){
                editObj.content[key] = editObj.cached[key];
            }
        } else {
            editObj.cached = new Object();            
            for (var key in editObj.content){
                editObj.cached[key] = editObj.content[key];
            }
        }
        editObj.editable = !editObj.editable ;
    }
    
}]);