//hotNotesCtrl.js

ctrls.controller("hotNotesController", ['$scope', 'Note', 'Comment', function(){
    
    // Initlizate the notes iist
    notesLib($scope, Note, Comment, {});
    
    $scope.search = function() {
        var arguments = {searchTerm: $scope.searchTerm};
        notesLib($scope, Note, Comment, search);
    }
}]);