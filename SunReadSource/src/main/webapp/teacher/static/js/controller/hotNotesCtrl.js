//hotNotesCtrl.js

ctrls.controller("hotNotesController", ['$scope', '$rootScope','NoteView', 'Teacher','config',
    function($scope, $rootScope, NoteView, Teacher, config){
    
    $scope.teacher = Teacher.get({id: $rootScope.id}, function(){
        
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
    });
        
    $scope.showLightBox = function(url){
        $scope.showLightBox.url = $scope.imageServer + url;
    }
}]);