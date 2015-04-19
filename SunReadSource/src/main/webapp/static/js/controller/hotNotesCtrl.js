//hotNotesCtrl.js

ctrls.controller("hotNotesController", ['$scope', 'NoteView', 'config',
    function($scope, NoteView, config){
    

        
    // The search method
    $scope.search = function(searchTerm){
        
        // Make an instance of the NoteView
        $scope.noteView = new NoteView();
        
        // Transmit arguments to search engine
        $scope.arguments = { sortBy: 'commentCount', searchTerm: searchTerm };
        $scope.noteView.ShowMoreNotes( $scope.arguments );
    }
    
    // List all the Notes
    $scope.search();
        
    // Get the image server
    $scope.imageServer = config.IMAGESERVER;
}]);