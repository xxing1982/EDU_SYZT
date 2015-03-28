//readingCenterMyNoteCtrl.js

ctrls.controller("readingCenterMyNoteController", ['$scope', 'Note', 'Comment', function ($scope, Note, Comment) {
	$scope.showMust = true;

    /*
        Hide all the comments of all notes
    */
    $scope.Notes = Note.get(function(){
        var content = $scope.Notes.content;
        for(var i = 0; i < content.length; i++){
            content[i].showComments = false;
        }
    });
    
    /*
        Show the comments of the note with the index and hide the last one
    */
	$scope.ShowComments = (function(){
        var last = 0;
        return function(index){
            
            // Initlizate some common values
            var notes = $scope.Notes.content;
            var currentNote = notes[index];
            
            // Toggle the comment show or hide and then save the last index 
            currentNote.showComments = !currentNote.showComments; 
            if (last !== index){
                notes[last].showComments = false;
                last = index;
            }
            
            // Get the comments by Note id and display
            if ( currentNote.showComments === true ){
                $scope.Comments = Comment.getByNoteId({id: currentNote.id});
            }
        };
    })();


}]);