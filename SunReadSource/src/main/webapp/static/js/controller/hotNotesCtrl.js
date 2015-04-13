//hotNotesCtrl.js

ctrls.controller("hotNotesController", ['$scope', 'NoteView',
    function($scope, NoteView){
    

        
    // The search method
    $scope.search = function(searchTerm){
        
        // Make an instance of the NoteView
        $scope.noteView = new NoteView();
        
        // Transmit arguments to search engine
        $scope.arguments = { serchTerm: searchTerm };
        $scope.noteView.ShowMoreNotes( $scope.arguments );
    }
    
    // List all the Notes
    $scope.search();
}]);