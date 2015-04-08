//personalProfile.js

ctrls.controller("personalProfileController", ['$rootScope', '$scope', 'User', function ($rootScope, $scope, User) {
    
    // Basic information
    $scope.basicInformation = new Object();
    $scope.basicInformation.content = { username: $rootScope.student.username,
                                        gender: $rootScope.student.gender,
                                        birthday: $rootScope.student.birthday,
                                        schoolId: $rootScope.student.schoolId,
                                        clazzId: $rootScope.student.clazzId };
    
    // Personal information
    $scope.personalInformation = new Object();
    $scope.personalInformation.content = { nickname: $rootScope.student.nickname,
                                           qqId: $rootScope.student.qqId,
                                           wechatId: $rootScope.student.wechatId,
                                           email: $rootScope.student.email,
                                           phoneNumber: $rootScope.student.phoneNumber };
    
    // Turn off the edit of personal infomation
    $scope.personalInformation.editable = false;
    
    
    // Invoke this with the entity object
    $scope.toggleEdit = function(editObj, save){
        
        if (save) {
            
            // Clone entity
            for (var key in editObj.cached){
                editObj.content[key] = editObj.cached[key];
            }
            
            // Update the user entity
            User.update({id: $rootScope.id}, editObj.cached);
        } else {
            
            // Create cached entity
            editObj.cached = new Object();
            
            // Clone entity
            for (var key in editObj.content){
                editObj.cached[key] = editObj.content[key];
            }
        }
        editObj.editable = !editObj.editable ;
    }
    
}]);