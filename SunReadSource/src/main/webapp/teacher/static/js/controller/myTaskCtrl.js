//myTaskCtrl.js
ctrls.controller("myTaskController",['$scope', '$rootScope', 'Teacher', 'Order', 'Loadable', 'Editable', 'Task',
	function($scope, $rootScope, Teacher, Order, Loadable, Editable, Task){
		
        // Get the teacher entity by rootScope id 
        $scope.teacher = Teacher.get({ id: $rootScope.id }, function(){
                
            // Get the orders pagable by teacher classId
            // The best practice of loadable
            // Create a pageable entity of actions
            $scope.orderLoadable = new Loadable();

            // Set the parameters of loadable
            $scope.orderLoadable.size = 5;
            $scope.orderLoadable.page = 0;

            // Set the $resource arguments like {by: "books"}
            $scope.orderLoadable.arguments = { by: 'clazzs', id: $scope.teacher.classId };

            // Build the loadable object
            $scope.orderLoadable.build(Order);

            // The index of the entities
            var index = 0;
            
            // Show the first page and append editable to every entity
            $scope.orderLoadable.get( function(){
                        
                // Make the editable object
                while (index < $scope.orderLoadable.entities.content.length) {
                    $scope.orderLoadable.entities.content[index].editable 
                        = new Editable($scope.orderLoadable.entities.content[index].task);
                    index ++;
                }
            
            });
            
            // The toggleEdit save
            $scope.toggleEditSave = function(order){

                // The toggle edit validation
                var toggleEditValidation = function(cached){
                    return ( cached.targetBookNum >= 0 && cached.targetPoint >=0 );
                };

                // The toggle edit transmit
                var toggleEditTransmit = function(cached){
//                    console.log( cached );
//                    console.log( order );
                    Task.update({ teacherId: $scope.teacher.id, 
                                  studentId: order.id, 
                                  targetBookNum: cached.targetBookNum,
                                  targetPoint: cached.targetPoint },{}
                    );
                };

                order.editable.toggleEdit(true, toggleEditValidation, toggleEditTransmit);
            };
        });
	}]);