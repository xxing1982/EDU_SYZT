//personalProfile.js

ctrls.controller("personalProfileController", 
        ['$rootScope', '$scope', 'User', 'Student', 'Campus', 'Class', 'Dropzone', 'config', 
        function ($rootScope, $scope, User, Student, Campus, Class, Dropzone, config) {
    
    // Invoke this with the entity object
    $scope.toggleEdit = function(editObj, save){

        if (save) {

            // Clone entity
            for (var key in editObj.cached){
                editObj.content[key] = editObj.cached[key];
            }

            // Update the user entity
            User.update({id: $rootScope.id}, editObj.cached);
        }
        else {

            // Create cached entity
            editObj.cached = new Object();

            // Clone entity
            for (var key in editObj.content){
                editObj.cached[key] = editObj.content[key];
            }
        }
        editObj.editable = !editObj.editable ;
    };
    
    // All information base on the student id in rootScope
    $scope.student = Student.get( {id: $rootScope.id}, function(){
        
        // Basic information
        $scope.basicInformation = new Object();
        $scope.basicInformation.content = { username: $scope.student.username,
                                            gender: $scope.student.gender,
                                            birthday: $scope.student.birthday,
                                            school: $scope.student.school,
                                            class: $scope.student.class };

        // Get the campus imformation
        $scope.campus = Campus.get( {id: $scope.student.campusId }, function(){
            $scope.basicInformation.content.campus = $scope.campus.name;
        });
        
        // Get the class imformation
        $scope.class = Class.get( {id: $scope.student.clazzId }, function(){
            $scope.basicInformation.content.class = $scope.class.name;
        });
        
        // Personal information
        $scope.personalInformation = new Object();
        $scope.personalInformation.content = { nickname: $scope.student.nickname,
                                               qqId: $scope.student.qqId,
                                               wechatId: $scope.student.wechatId,
                                               email: $scope.student.email,
                                               phoneNumber: $scope.student.phoneNumber };

        // Turn off the edit of personal infomation
        $scope.personalInformation.editable = false;

        // Check the orginal avatar url
        $scope.avatarUrl = $scope.student.picture === "" ? "../static/img/myBookshelf/addPhoto.png" : config.IMAGESERVER + $scope.student.picture ;

        // Save the orginal avatar
        $scope.avatarOrg = $scope.student.picture;

        // Image uploader
        $scope.dropzone = Dropzone(config.USERICON, function(url){
             User.update({id: $rootScope.id}, {picture: url});
        } );

        // Update remove file callback
        $scope.dropzone.on('removedfile', function(){
            User.update({id: $rootScope.id}, {picture: $scope.avatarOrg});
        });

        // Get the image server
        $scope.imageServer = config.IMAGESERVER;
        
        // Security setting
        $scope.securitySetting = new Object();
        $scope.securitySetting.content = new Object();
        $scope.securitySetting.cached = {password: "", confirmPassword: ""};
        
        // Turn off the edit of security setting
        $scope.securitySetting.editable = false;

        // Invoke this to verify the password and save
        $scope.securitySetting.togglePasswordEdit = function(save){
            
            // Toggle and save the password
            $scope.toggleEdit(this, save); 
            
            if (!save){
                // Clean the form
                this.content = new Object();
                this.cached = new Object();
                
            } else {

                // ToggleEdit the password when avaliable
                delete this.cached.confirmPassword;
            }
        }

    } );
}]);