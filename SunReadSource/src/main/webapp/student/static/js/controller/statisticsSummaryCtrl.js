//statisticsSummary.js

ctrls.controller("statisticsSummaryController", ['$scope' , '$rootScope', 'Student', 'Semester', 'Campus', 'Class', 'BookshelfStatistics',
                                                  function ($scope, $rootScope, Student, Semester, Campus, Class, BookshelfStatistics) {
                            
    function findMaxAndIndex(arr){
        var max = arr[0];
        var index;
        for (var i = 0; i < arr.length; i ++) {
            if (max < arr[i]){
                max = arr[i];
                index = i;
            }
        }
        return { max: max, index: index };    
    }
                                                      
                                                      
    // Initlizate the dropdown statues
    $scope.semestersStatuses = [];
    $scope.semestersSelected_status = 0;
                                                      
    // Initlizate the statistics
    $scope.statistics = { summaryHeader: {},
                          basicInformations : {},
                          readingQuality: {},
                          semesterReadings: {},
                          semesterPoints: {},
                          readingCategory : {},
                          booksList: {},
                          notesList: {} };
                                                      
    // Get student by id 
    $scope.student = Student.get({id: $rootScope.id}, function(){
        
        // Get Semesters of student
        $scope.semesters = Semester.get({by: 'student', id: $rootScope.id}, function(){
            
            // The method to update statistics by semester
            $scope.semesters.updateStatistics = function(){
                
                // Get current semester user 
                var semester = $scope.semesters[$scope.semestersSelected_status];
                var student = $scope.student;
                
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
                summaryHeader.username = student.username;
                

                var basicInformations = $scope.statistics.basicInformations;
                var semesterReadings = $scope.statistics.semesterReadings;
                var semesterPoints = $scope.statistics.semesterPoints;
                
                BookshelfStatistics.get({studentId: student.id, semesterId: semester.id}, function(bookshelfStatistics){
                   
                    // Update Basic informations
                    basicInformations.level = student.statistic.level;
                    basicInformations.coin = student.statistic.coin;
                    basicInformations.semesterReadNum = bookshelfStatistics.semesterReadNum;
                    basicInformations.semesterVerifiedNum = bookshelfStatistics.semesterVerified.length;
                    basicInformations.point = student.statistic.point;
                    basicInformations.semesterVerifiedRate = Math.floor(basicInformations.semesterVerifiedNum / basicInformations.semesterReadNum * 100);
                
                    // Update semester informations
                    semesterReadings.semesterReadNum = bookshelfStatistics.semesterReadNum;
                    semesterReadings.year = parseInt( summaryHeader.startTime.split("-")[0] );
                    semesterReadings.startMonth = parseInt( summaryHeader.startTime.split("-")[1] );
                    semesterReadings.monthlyVerifiedNums = bookshelfStatistics.monthlyVerifiedNums;
                    var maxMonthlyVerifiedNumAndIndex = findMaxAndIndex( bookshelfStatistics.monthlyVerifiedNums );
                    semesterReadings.maxMonthlyVerifiedNum = maxMonthlyVerifiedNumAndIndex.max;
                    semesterReadings.maxMonthlyVerifiedNumMonth = maxMonthlyVerifiedNumAndIndex.index + semesterReadings.startMonth;
                
                    // Update semester points
                    semesterPoints.year = parseInt( summaryHeader.startTime.split("-")[0] );
                    semesterPoints.startMonth = parseInt( summaryHeader.startTime.split("-")[1] );
                    semesterPoints.monthlyPoints = bookshelfStatistics.monthlyPoints;
                    var maxMonthlyPointAndIndex = findMaxAndIndex( bookshelfStatistics.monthlyPoints );
                    semesterPoints.maxMonthlyPoint = maxMonthlyPointAndIndex.max;
                    semesterPoints.maxMonthlyPointMonth = maxMonthlyPointAndIndex.index + semesterPoints.startMonth;
                    semesterPoints.totalPoint = eval( bookshelfStatistics.monthlyPoints.join("+") );
                    
                    // Update reading category
//                    readingCategory.
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