//myTaskCtrl.js
ctrls.controller("myTaskController",['$scope', '$rootScope', 'Teacher', 'Order', 'Loadable', 'Editable',
	function($scope, $rootScope, Teacher, Order, Loadable, Editable){
		
        // Get the teacher entity by rootScope id 
        $scope.teacher = Teacher.get({ id: $rootScope.id }, function(){
                
            // Get the orders pagable by teacher classId
            // The best practice of loadable
            // Create a pageable entity of actions
            $scope.orderLoadable = new Loadable();

            // Set the parameters of loadable
            $scope.orderLoadable.size = 4;
            $scope.orderLoadable.page = 0;

            // Set the $resource arguments like {by: "books"}
            $scope.orderLoadable.arguments = { by: 'clazzs', id: $scope.teacher.classId };

            // Build the loadable object
            $scope.orderLoadable.build(Order);

            // Show the first page
            $scope.orderLoadable.get( function(){
                        
                // Make the editable object
                
            
            });


        });
        
        // The edit of readingnum and points
        $scope.edit = function(task){
            $scope.editable = new Editable(task);
        };
	}]);