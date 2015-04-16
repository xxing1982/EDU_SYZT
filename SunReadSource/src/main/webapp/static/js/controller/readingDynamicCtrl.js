//readingDynamic.js

ctrls.controller("readingDynamicController", ['$scope', '$rootScope', 'NoteView', 'Student', 'Campus',  function ($scope, $rootScope, NoteView, Student, Campus) {
    
    // Get student info and campus
    var student = Student.get({id : $rootScope.id}, function(){
        
        // Get headmaster
        $scope.campus = Campus.get({id: student.campusId});
    });

    
    // Make an instance of the NoteView
    $scope.noteView = new NoteView();

    // Transmit arguments to search engine
    $scope.noteView.ShowMoreNotes( {page: 0, size: 3, direction: "DESC", sortBy: "commentCount"} );
}]);