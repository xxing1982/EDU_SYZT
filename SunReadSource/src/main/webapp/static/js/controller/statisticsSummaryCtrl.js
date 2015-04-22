//statisticsSummary.js

ctrls.controller("statisticsSummaryController", ['$scope' , '$rootScope', 'Student', 'Semester',
                                                  function ($scope, $rootScope, Student, Semester) {
                                                      
    // Initlizate the dropdown statues
    $scope.semestersStatuses = [];
    $scope.semestersSelected_status = 0;
                                                      
    // Get student by id 
    $scope.student = Student.get({id: $rootScope.id}, function(){
        
        // Get Semesters of student
        $scope.semesters = Semester.get({by: 'student', id: $rootScope.id}, function(){
            
            // Initlizate the dropdown statues
            $scope.semestersStatuses.length = 0;
            
            // The method to get statistics by semester
            $scope.semesters.getStatistics = function(){
            
            };
            
                            
            // Grades
            var Grades = ['一', '二', '三', '四', '五', '六'];
            
            for (var i = 0; i < $scope.semesters.length; i ++ ){
                
                // Push dropdown menu
                $scope.semestersStatuses.push({id: i, 
                                               name: Grades[1 - Math.ceil(i / 2)] + "年级" + $scope.semesters[i].semester,
                                               callback: $scope.semesters.getStatistics 
                                               });
                
            }
        
        });
    });     
                                                     
                                                
    
        
}]);