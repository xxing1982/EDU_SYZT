//readingCenterMyNoteCtrl.js

ctrls.controller("readingCenterMyNoteController", ['$rootScope', '$scope', 'NoteView',
    function($rootScope, $scope, NoteView){
    
    // Get the user id form rootScope
    $scope.arguments = { by: "users",
                      id: $rootScope.id};
    
    // Initlizate the noteView entity
    $scope.noteView = new NoteView();
    $scope.noteView.ShowMoreNotes($scope.arguments);
    console.log($scope.noteView);
} ]);