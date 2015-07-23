//myTaskCtrl.js
ctrls.controller("myTaskController",['$scope', '$rootScope', 'Teacher', 'Order', 'Class', 'Loadable', 'Editable', 'Task', 'Tasks',
	function($scope, $rootScope, Teacher, Order, Class, Loadable, Editable, Task, Tasks){

        // Get the teacher entity by rootScope id
        $scope.teacher = Teacher.get({ id: $rootScope.id }, function(){

            // Get the class by teacher classId
            $scope.class = Class.get({ id: $scope.teacher.currentClassId });

            // Get the orders pagable by teacher classId
            // The best practice of loadable
            // Create a pageable entity of actions
            $scope.orderLoadable = new Loadable();

            // Initlizate the orderLoadable
            $scope.initOrderLoadable = function(){

                // Set the parameters of loadable
                $scope.orderLoadable.size = 5;
                $scope.orderLoadable.page = 0;

                // Set the $resource arguments like {by: "books"}
                $scope.orderLoadable.arguments = { by: 'clazzs', id: $scope.teacher.currentClassId, sortBy: "id" };

                // Build the loadable object
                $scope.orderLoadable.build(Order);

                // The index of the entities
                var index = 0;

                // Show the first page and append editable to every entity
                $scope.orderLoadable.get( function(){

                    // Make the editable object
                    var orders = $scope.orderLoadable.entities.content;
                    while (index < orders.length) {
                        var order = orders[index];
                        if (order.task !== null){
                            order.editable = new Editable(order.task);
                            index ++;
                        } else {
                            orders.splice(index, 1);
                        }
                    }

                });
            };

            $scope.initOrderLoadable();


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

            // Publish the tasks by teacher id
            $scope.publish = function(){
                if ( $scope.targetBookNum && $scope.targetPoint ){
                    Tasks.update({ teacherId: $scope.teacher.id,
                                   targetBookNum: $scope.targetBookNum,
                                   targetPoint: $scope.targetPoint },{}, function(){
                                        $rootScope.modal = {title: "发布奖励", content: "奖励发布成功！"};
                                        $('#alert-modal').modal();

                                        // Clear the targetBookNum targetPoint and orderloadable
                                        $scope.targetBookNum = undefined;
                                        $scope.targetPoint = undefined;
                                        $scope.initOrderLoadable();
                                   });

                }
            };
        });
	}]);
