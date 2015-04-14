//readingDynamic.js

ctrls.controller("readingDynamicController", ['$scope', 'NoteView', function ($scope, NoteView) {
    
    // Make an instance of the NoteView
    $scope.noteView = new NoteView();

    // Transmit arguments to search engine
    $scope.noteView.ShowMoreNotes( {page: 0, size: 3, direction: "DESC", sortBy: "commentCount"} );
}]);