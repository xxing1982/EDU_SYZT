//statisticsSummary.js

ctrls.controller("statisticsSummaryController", ['$scope' , '$rootScope', '$resource', 'config', 'Student', 'Semester', 'Campus', 'Class', 'BookshelfStatistics', 'Note', 'PointHistory', 'CoinHistory',
                                                  function ($scope, $rootScope, $resource, config, Student, Semester, Campus, Class, BookshelfStatistics, Note, PointHistory, CoinHistory) {
    
    var Verifyexams = $resource( config.HOST + "verifyexams/:by/:studentId/:semesterId",
            {by:'@by', studentId:'@studentId', semesterId:'@semesterId'},{} );
                                                      
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
    
    var colorList = [ 0xEABE94, 0xCCDF9F, 0xB3DCDF, 0xEDD685, 0xE99999 ];
    var colorIndex = 0;
    function generateRamdomColor(){
        var index = colorIndex % colorList.length;
        var divider = Math.floor( colorIndex / colorList.length ) * 0.3 + 1;
        var subtration = 0x000F20 * Math.floor( colorIndex / colorList.length ) ;
        colorIndex ++ ;
        return "#" + ( Math.floor( ( colorList[ index ] - subtration ) / divider ) ).toString(16).toUpperCase();
    }          
    
    function drawPie(pie_data){
        var pie_options = options = {
            //Boolean - Whether we should show a stroke on each segment
            segmentShowStroke : false,

            //String - The colour of each segment stroke
            segmentStrokeColor : "#fff",

            //Number - The width of each segment stroke
            segmentStrokeWidth : 2,

            //Number - The percentage of the chart that we cut out of the middle
            percentageInnerCutout : 0, // This is 0 for Pie charts

            //Number - Amount of animation steps
            animationSteps : 100,

            //String - Animation easing effect
            animationEasing : "easeOutBounce",

            //Boolean - Whether we animate the rotation of the Doughnut
            animateRotate : true,

            //Boolean - Whether we animate scaling the Doughnut from the centre
            animateScale : false,

            //String - A legend template
            legendTemplate : "<ul class=\"<%=name.toLowerCase()%>-legend\"><% for (var i=0; i<segments.length; i++){%><li><span style=\"background-color:<%=segments[i].fillColor%>\"></span><%if(segments[i].label){%><%=segments[i].label%><%}%></li><%}%></ul>"

        };

        // Get the context of the canvas element we want to select
        var pie_ctx = document.getElementById("myPieChart").getContext("2d");

        // For a pie chart
        var myPieChart = new Chart(pie_ctx).Pie(pie_data, pie_options);
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
                
                // Initlizate the color generator
                colorIndex = 0;
                
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
                var readingQuality = $scope.statistics.readingQuality;
                var semesterReadings = $scope.statistics.semesterReadings;
                var semesterPoints = $scope.statistics.semesterPoints;
                var readingCategory = $scope.statistics.readingCategory;
                var booksList = $scope.statistics.booksList;
                var notesList = $scope.statistics.notesList;
                
                // Update reading quality
                Verifyexams.get({ by: "passrate", studentId: student.id, semesterId: semester.id}, function(passRate){
                    readingQuality.average = passRate.average;
                    readingQuality.firstpassrate = passRate.firstpassrate;
                    readingQuality.secondpassrate = passRate.secondpassrate;
                });
                
                BookshelfStatistics.get({studentId: student.id, semesterId: semester.id}, function(bookshelfStatistics){
                   
                    // Update Basic informations
                    basicInformations.level = Math.ceil( ($scope.semesters.length - $scope.semestersSelected_status) / 2 );
                    CoinHistory.get({by: "semesters", id: semester.id}, function(coinhistories){
                        basicInformations.coin = 0;
                        for (var i = 0; i < coinhistories.monthlyCoins.length; i++ ) {
                            basicInformations.coin += coinhistories.monthlyCoins[i];
                        }
                    });
                    basicInformations.semesterReadNum = bookshelfStatistics.semesterReadNum;
                    basicInformations.semesterVerifiedNum = bookshelfStatistics.semesterVerified.length;
                    PointHistory.get({by: "semesters", id: semester.id}, function(pointhistories){
                        basicInformations.point = 0;
                        for (var i = 0; i < pointhistories.monthlyPoints.length; i++ ) {
                            basicInformations.point += pointhistories.monthlyPoints[i];
                        }
                        
                        // Update semester points
                        semesterPoints.year = parseInt( summaryHeader.startTime.split("-")[0] );
                        semesterPoints.startMonth = parseInt( summaryHeader.startTime.split("-")[1] );
                        semesterPoints.monthlyPoints = pointhistories.monthlyPoints;
                        var maxMonthlyPointAndIndex = findMaxAndIndex( pointhistories.monthlyPoints );
                        semesterPoints.maxMonthlyPoint = maxMonthlyPointAndIndex.max;
                        semesterPoints.maxMonthlyPointMonth = maxMonthlyPointAndIndex.index + semesterPoints.startMonth;
                        semesterPoints.totalPoint = eval( pointhistories.monthlyPoints.join("+") );
                        semesterPoints.width = Math.floor( 90 / semesterPoints.monthlyPoints.length );

                    });
                    basicInformations.semesterVerifiedRate = basicInformations.semesterReadNum !== 0 ? Math.floor(basicInformations.semesterVerifiedNum / basicInformations.semesterReadNum * 100) : 0;
                
                    // Update semester informations
                    semesterReadings.semesterReadNum = bookshelfStatistics.semesterReadNum;
                    semesterReadings.year = parseInt( summaryHeader.startTime.split("-")[0] );
                    semesterReadings.startMonth = parseInt( summaryHeader.startTime.split("-")[1] );
                    semesterReadings.monthlyVerifiedNums = bookshelfStatistics.monthlyVerifiedNums;
                    var maxMonthlyVerifiedNumAndIndex = findMaxAndIndex( bookshelfStatistics.monthlyVerifiedNums );
                    semesterReadings.maxMonthlyVerifiedNum = maxMonthlyVerifiedNumAndIndex.max;
                    semesterReadings.maxMonthlyVerifiedNumMonth = maxMonthlyVerifiedNumAndIndex.index + semesterReadings.startMonth;
                
                    // Update reading category
                    readingCategory.categories = {};
                    readingCategory.categoriesNum = 0;
                    readingCategory.maxCategoryNum = 0;
                    readingCategory.maxCategoryName;
                    readingCategory.CategoriesSum = 0;
                    for ( var i = 0; i < bookshelfStatistics.semesterVerified.length; i ++ ) {
                        var name = bookshelfStatistics.semesterVerified[i].category;
                        var category = readingCategory.categories[ name ];
                        if ( category === undefined ) {
                            readingCategory.categories[ name ] = 1;
                        } else {  
                            category ++;                            
                        }
                    }
                    
                    var pie_data = [];
                    for ( var name in readingCategory.categories ) {
                        var category = readingCategory.categories[ name ];
                        if ( readingCategory.maxCategoryNum < category ) {
                            readingCategory.maxCategoryNum = category;
                            readingCategory.maxCategoryName = name;
                        }
                        readingCategory.categoriesNum ++;
                        readingCategory.CategoriesSum += category;
                        var color = generateRamdomColor();
                        pie_data.push({ value: category,
                                        color: color,
                                        highlight: color,
                                        label: name });
                    }
                    readingCategory.maxCategoryRate = Math.floor( readingCategory.maxCategoryNum * 100 / readingCategory.CategoriesSum );
                    drawPie(pie_data);
                    
                    // Update books list
                    booksList.semesterVerified = bookshelfStatistics.semesterVerified;
                });
                
                // Update notes list
                Note.query({ by: 'semesters', id: semester.id, userId: $rootScope.id }, function(notes){
                    notesList.notes = notes;
                    notesList.count = notes.length;
                    notesList.commentCount = 0;
                    for ( var i = 0; i < notes.length; i ++ ) {
                         notesList.commentCount += notes[i].commentCount;
                    } 
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