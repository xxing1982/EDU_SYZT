//statisticsSummary.js

ctrls.controller("statisticsSummaryController", ['$scope' , '$rootScope', 'Student', 'Semester', 'Campus', 'Class',
                                                  function ($scope, $rootScope, Student, Semester, Campus, Class) {
                                                      
    // Initlizate the dropdown statues
    $scope.semestersStatuses = [];
    $scope.semestersSelected_status = 0;
                                                      
    // Initlizate the statistics
    $scope.statistics = { summaryHeader: {},
                          basicInformations : {},
                          readingQuality: {},
                          semesterInformations: {},
                          readingCategory : {},
                          booksList: {},
                          notesList: {} };
                                                      
    // Get student by id 
    $scope.student = Student.get({id: $rootScope.id}, function(){
        
        // Get Semesters of student
        $scope.semesters = Semester.get({by: 'student', id: $rootScope.id}, function(){
            
            // The method to update statistics by semester
            $scope.semesters.updateStatistics = function(){
                
                // Get current semester
                var semester = $scope.semesters[$scope.semestersSelected_status];
                
                // Update summaryHeader
                var summaryHeader = $scope.statistics.summaryHeader;
                summaryHeader.startTime = semester.startTime;
                summaryHeader.endTime = semester.endTime;
                Campus.get({id: $scope.student.campusId}, function(campus){
                    summaryHeader.campus = campus.name;
                });
                Class.get({id: $scope.student.clazzId}, function(clazz){
                    summaryHeader.class = clazz.name;
                });
                
            };
            
            // Initlizate the dropdown statues
            $scope.semestersStatuses.length = 0;
            
            for (var i = 0; i < $scope.semesters.length; i ++ ){
                
                // Push dropdown menu
                $scope.semestersStatuses.push({ id: i, 
                                               name: $scope.semesters[i].semester,
                                               callback: $scope.semesters.updateStatistics });
                
            }
            
            // Chooese the latest semester 
            $scope.semestersSelected_status = 0;
            $scope.semesters.updateStatistics();
        });
    });
                                                      
}]);