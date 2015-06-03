ctrls.controller("teachingCenterAddToShelfController",['$scope', '$rootScope','Teacher','Class','GetBookshelvesByClass','AddRecommends',
	function($scope, $rootScope,Teacher,Class,GetBookshelvesByClass,AddRecommends){
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
        $scope.bookshelfLoadable = new Loadable();

        // Set the parameters of loadable
        $scope.bookshelfLoadable.size = 5;
        $scope.bookshelfLoadable.page = 0;

        // Set the $resource arguments like {by: "books"}
        $scope.bookshelfLoadable.arguments = {classId: $scope.teacher.classId};

        // Build the loadable object
        $scope.bookshelfLoadable.build(GetBookshelvesByClass);

        // Show the first page and append editable to every entity
        $scope.bookshelfLoadable.get();

				console.log($scope.bookshelfLoadable);

        // Make the checklist model
        $scope.bookshelfLoadable.selected = [];

        // Publish the selected entities
        $scope.bookshelfLoadable.publish = function(){
            for(var i = 0; i < this.selected.length; i++ ){
                // if (this.selected[i].targetBookNum &&  this.selected[i].targetPoint){
                //     Task.update({ teacherId: $scope.teacher.id,
                //                   studentId: this.selected[i].id,
                //                   targetBookNum: this.selected[i].targetBookNum,
                //                   targetPoint: this.selected[i].targetPoint },{}
                //     );
	              // }
								AddRecommends.save({});
            }
        };

        // The select all method
        $scope.bookshelfLoadable.selectAll = function(){
            if (!this.selectedAllValue) {
                this.selected = angular.copy(this.entities.content);
            } else {
                this.selected = [];
            }
            this.selectedAllValue = !this.selectedAllValue;
        };
    });


  }]);
