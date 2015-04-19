//readingCenterMyNoteCtrl.js

ctrls.controller("readingCenterMyNoteController", ['$rootScope', '$scope', 'NoteView', 'config',
    function($rootScope, $scope, NoteView, config){
    
    // Get the user id form rootScope
    $scope.arguments = { by: "users",
                         id: $rootScope.id};
    
    // Initlizate the noteView entity
    $scope.noteView = new NoteView();
    $scope.noteView.ShowMoreNotes($scope.arguments);
        
    // Get the image server
    $scope.imageServer = config.IMAGESERVER;
} ]);