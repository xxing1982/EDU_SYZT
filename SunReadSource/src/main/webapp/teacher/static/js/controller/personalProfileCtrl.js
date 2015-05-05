//personalProfile.js

ctrls.controller("personalProfileController", ['$rootScope', '$scope', 'User', 'Teacher', 'Dropzone', 'config',  function ($rootScope, $scope, User, Teacher, Dropzone, config) {
    
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
    
    // All information base on the teacher id in roootScope
    $scope.teacher = Teacher.get( {id: $rootScope.id}, function(){
        
        // Basic information
        $scope.basicInformation = new Object();
        $scope.basicInformation.content = { username: $scope.teacher.username,
                                            gender: $scope.teacher.gender,
                                            birthday: $scope.teacher.birthday,
                                            school: $scope.teacher.school,
                                            class: $scope.teacher.class };

        // Personal information
        $scope.personalInformation = new Object();
        $scope.personalInformation.content = { nickname: $scope.teacher.nickname,
                                               qqId: $scope.teacher.qqId,
                                               wechatId: $scope.teacher.wechatId,
                                               email: $scope.teacher.email,
                                               phoneNumber: $scope.teacher.phoneNumber };

        // Turn off the edit of personal infomation
        $scope.personalInformation.editable = false;

        // Check the orginal avatar url
        $scope.avatarUrl = $scope.teacher.picture === "" ? "../static/img/myBookshelf/addPhoto.png" : config.IMAGESERVER + $scope.teacher.picture ;

        // Save the orginal avatar
        $scope.avatarOrg = $scope.teacher.picture;

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
        
        // Hide the hint of password
        $scope.securitySetting.tooShort = false;
        $scope.securitySetting.different = false;

        // Invoke this to verify the password and save
        $scope.securitySetting.togglePasswordEdit = function(save){

            // Clean the form
            if (!save){
                this.content = new Object();
                this.cached = new Object();
            }
            this.tooShort = false;
            this.different = false;

            // ToggleEdit the password when avaliable
            if (save){
                if (this.validatePassword()) { 
                    delete this.cached.confirmPassword;
                    $scope.toggleEdit(this, save); 
                }
            } else {
                $scope.toggleEdit(this, save); 
            }
        }

        // Invoke this to verify the password and save
        $scope.securitySetting.validatePassword = function(){
            
            // Check the length of password
            this.tooShort = this.cached.password.length < 6;

            // Password and confirmPassword not equal
            this.different = this.cached.password !== this.cached.confirmPassword 
                             && this.cached.confirmPassword !== undefined;
            return !this.different && !this.tooShort;

        }
    } );
}]);