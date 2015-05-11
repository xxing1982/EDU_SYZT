ctrls.controller("teachingCenterAddToShelfController",['$scope', '$rootScope', 'Teacher', 'Hotreader', 'Class', 'Task',
	function($scope, $rootScope, Teacher, Hotreader, Class, Task){
    // Initlizate the dropdown statues
    $scope.campusStatuses = [];
    $scope.campusSelected_status = 0;

    $scope.gradeStatuses = [];
    $scope.gradeSelected_status = 0;

    $scope.classStatuses = [];
    $scope.classSelected_status = 0;

    // Get the teacher entity by rootScope id
    $scope.teacher = Teacher.get({ id: $rootScope.id }, function(){

        var gradeName = ['一', '二', '三', '四', '五', '六'];
        var index = 0;

        // Get the class by teacher classId
        $scope.class = Class.get({ id: $scope.teacher.classId }, function(){
            $scope.campusStatuses.push({ id: index, name: $scope.class.campusName});
            $scope.gradeStatuses.push({ id: index, name: gradeName[$scope.class.grade - 1] + '年级'});
            $scope.classStatuses.push({ id: index, name: $scope.class.name});
        });


        // Get the hotreaers pagable by teacher classId
        // The best practice of loadable
        // Create a pageable entity of actions
        $scope.hotreaderLoadable = new Loadable();

        // Set the parameters of loadable
        $scope.hotreaderLoadable.size = 5;
        $scope.hotreaderLoadable.page = 0;

        // Set the $resource arguments like {by: "books"}
        $scope.hotreaderLoadable.arguments = { by: 'clazz', id: $scope.teacher.classId, sortBy: 'testPasses'};

        // Build the loadable object
        $scope.hotreaderLoadable.build(Hotreader);

        // Show the first page and append editable to every entity
        $scope.hotreaderLoadable.get();

        // Make the checklist model
        $scope.hotreaderLoadable.selected = [];

        // Publish the selected entities
        $scope.hotreaderLoadable.publish = function(){
            for(var i = 0; i < this.selected.length; i++ ){
                if (this.selected[i].targetBookNum &&  this.selected[i].targetPoint){
                    Task.update({ teacherId: $scope.teacher.id,
                                  studentId: this.selected[i].id,
                                  targetBookNum: this.selected[i].targetBookNum,
                                  targetPoint: this.selected[i].targetPoint },{}
                    );
                }
            }
        };

        // The select all method
        $scope.hotreaderLoadable.selectAll = function(){
            if (!this.selectedAllValue) {
                this.selected = angular.copy(this.entities.content);
            } else {
                this.selected = [];
            }
            this.selectedAllValue = !this.selectedAllValue;
        };
    });


  }]);
