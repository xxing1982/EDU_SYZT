// Lib for Note contrllers

var notesLib = function ($scope, Note, Comment) {

    /*
        Some constants 
    */
    var notePageSize = 10;
    var commentPageSize = 5;
    var stateTexts = { more : "加载更多", loading: "更多加载中...", nomore: "没有了"};
    
    
    /*
        Initlizate some variables in $scope
    */
    $scope.showMust = true;
    $scope.loadingState = stateTexts.more;

    /*
        Hide all the comments of all notes
    */
    $scope.Notes = Note.get({ page: 0, size: notePageSize, direction: "DESC", sortBy: "id"}, function(){
        var content = $scope.Notes.content;
        for(var i = 0; i < content.length; i++){   
            content[i].showComments = false;
        }
    });
    
    
    /*
        Invoke this method to load more notes
    */
    $scope.ShowMoreNotes = (function(){
        
        // Some states of the closure
        var page = 1;
        var size = notePageSize;
        var finished = false;
        
        return function (){
            // Change the loading state to loading
            $scope.loadingState = stateTexts.loading;
    
            // GET the Notes entity
            var newPage = Note.get({ page: page, size: size, direction: "DESC", sortBy: "id"}, function(){ 
                if (newPage.lastPage){
                    // Get the last page of the Notes, 
                    // Change the state of the loading state and turn on finished
                    $scope.loadingState = stateTexts.nomore;
                    if (!finished) {
                        $scope.Notes.content = $scope.Notes.content.concat(newPage.content);
                    }
                    finished = true;
                } else {
                    finished = false;
                    $scope.loadingState = stateTexts.more;
                    $scope.Notes.content = $scope.Notes.content.concat(newPage.content);
                    page ++;
                }
            });
        };
    })();


    /*
        Invoke this method to load more comments
    */
    $scope.ShowMoreComments = (function(){
        
        // Some states of the closure
        var page = 1;
        var size = commentPageSize;
        var finished = false;
        
        return function (note){
            // Change the loading state to loading
            note.loadingState = stateTexts.loading;
    
            // GET the Comments entity
            var newPage = Comment.get({by: "notes", id: note.id, page: page, size: size, sortBy: "id", direction: "DESC"}, function(){               
                if (newPage.lastPage){
                    // Get the last page of the Comments, 
                    // Change the state of the loading state and turn on finished
                    note.loadingState = stateTexts.nomore;
                    if (!finished) {
                        note.Comments.content = note.Comments.content.concat(newPage.content);
                    }
                    finished = true;
                } else {
                    finished = false;
                    note.loadingState = stateTexts.more;
                    note.Comments.content = note.Comments.content.concat(newPage.content);
                    page ++;
                }
            });
        };
    })();

    /*
        Show the comments of the note with the index and hide the last one
    */
	$scope.ShowComments = (function(){
        var last = 0;
        var page = 0;
        return function(note){
            
            // Toggle the comment show or hide and then save the last index 
            note.showComments = !note.showComments; 
            
            // Initlizate the loadingstate of the comments
            note.loadingState = stateTexts.more;
            
            // Get the comments by Note id and display
            if ( note.showComments === true ){
                var Comments = Comment.get(
                    {by: "notes", id: note.id, page: 0, size: commentPageSize, sortBy: "id", direction: "DESC"},
                    function(){
                        note.Comments = Comments;
                        note.showShowMoreComment = (Comments.totalElements <= commentPageSize) ? false : true ;
                });
            }
        };
    })();


    $scope.addComment = function(note){
        // Check the content of comment is avaliable
        var content = note.newCommentContent;
        if ( content === "" || content === undefined ) return;
        
        // Get the comments reference and set the comment entity
        var comments = note.Comments.content;
        var comment = {content: content};
        
        // POST the comment entity by Comment factory
        Comment.save({by: "notes", id: note.id}, comment);
        
        // Add the comment entity to the top of Comments list
        comments.unshift(comment);
        
        // Reset the form 
        note.newCommentContent = "";
        
        // Add the count of comments
        note.commentCount ++;
    }
};