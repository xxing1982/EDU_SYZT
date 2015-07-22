//readingDynamic.js

ctrls.controller("readingDynamicController", ['$scope', '$rootScope', 'NoteView', 'Teacher', 'Campus', 'Action', 'Pageable', 'Hotclazz', 'Hotreader', 'config',
    function ($scope, $rootScope, NoteView, Teacher, Campus, Action, Pageable, Hotclazz, Hotreader, config) {



    // Get teacher info and campus
    var teacher = Teacher.get({id : $rootScope.id}, function(){

        // Get headmaster
        $scope.campus = Campus.get({id: teacher.campusId});
        console.log($scope.campus);

        // Create a Hotclazzs entitiy
        $scope.hotClazzs = Hotclazz.get({by: 'campuses', id: teacher.campusId, page: 0, size: 3 });


        // Create a Hotreaders entitiy
        $scope.hotReaders = Hotreader.get({by: 'campus', id: teacher.campusId, page: 0, size: 3, sortBy: "point" });
    });



    // Create a pageable entity of actions
    $scope.actionPageable = new Pageable();

    // Set the parameters of pageable
    $scope.actionPageable.size = 5;

    // Build the pageable object
    $scope.actionPageable.build(Action);

    // Show the page 1
    $scope.actionPageable.showPage(1);



    // Make an instance of the NoteView
    $scope.noteView = new NoteView();

    // Transmit arguments to search engine
    $scope.noteView.ShowMoreNotes( {page: 0, size: 3, direction: "DESC", sortBy: "commentCount"} );

    // Get the image server
    $scope.imageServer = config.IMAGESERVER;
}]);
