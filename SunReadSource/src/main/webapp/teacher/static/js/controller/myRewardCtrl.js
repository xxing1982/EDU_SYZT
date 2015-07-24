//myRewardCtrl.js
ctrls.controller("myRewardController", ['$scope', '$rootScope', 'Teacher', 'Class', 'CoinHistory', 'Loadable', 'Editable',
	function($scope, $rootScope, Teacher, Class, CoinHistory, Loadable, Editable){

        // Get the teacher entity by rootScope id
        $scope.teacher = Teacher.get({ id: $rootScope.id }, function(){

            // Get the class by teacher classId
            $scope.class = Class.get({ id: $scope.teacher.currentClassId }, function(){
                // Get the coinhistory pagable by teacherid
                // The best practice of loadable
                // Create a pageable entity of actions
                $scope.coinhistoryLoadable = new Loadable();

                // Set the parameters of loadable
                $scope.coinhistoryLoadable.size = 5;
                $scope.coinhistoryLoadable.page = 0;

                // Set the $resource arguments like {by: "books"}
                $scope.coinhistoryLoadable.arguments = { by: 'class', id: $scope.class.id, sortBy: 'id', direction: 'DESC' };

                // Build the loadable object
                $scope.coinhistoryLoadable.build(CoinHistory);

                // The index of the entities
                var index = 0;

                // Show the first page and append editable to every entity
                $scope.coinhistoryLoadable.get();
            });
        });

        $scope.showRewordReason=function(reason){
            $rootScope.modal = {title: "奖励原因", content: reason};
            $('#alert-modal').modal();
        }
	}]);
