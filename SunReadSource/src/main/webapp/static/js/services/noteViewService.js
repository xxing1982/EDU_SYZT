/* 
    Note view object 
*/
var NoteView = function () {
    
    // Some states of the closure
    this.Notes = {page: 0,
                  size: this.notePageSize, 
                  finished: false, 
                  content: []};
};


/*
    Some constants 
*/
NoteView.prototype.notePageSize = 2;
NoteView.prototype.commentPageSize = 5;
NoteView.prototype.stateTexts = { more : "加载更多", loading: "更多加载中...", nomore: "没有了"};

/*
    Invoke this method to load more notes
*/
NoteView.prototype.ShowMoreNotes = function (arguments){
        
    // Change the loading state to loading
    this.loadingState = this.stateTexts.loading;
    
    // Make the reference to the Notes object
    var Notes = this.Notes;

    // Make the reference to the NoteView object
    var NoteView = this;

    // GET the Notes entity
    var newPage = this.Note.get(
        $.extend({}, { page: Notes.page, size: Notes.size, direction: "DESC", sortBy: "id"}, arguments), function(){ 
            
            /*
                Hide all the comments of all 
                notes and initlizate the comment properties
            */
            var content = newPage.content;
            
            for(var i = 0; i < content.length; i++){   
                content[i].showComments = false;
                content[i].showShowMoreComment = content[i].commentCount > Notes.size;
            }
            
            
            /*
                Check the lastPage flag and set stateTexts
            */
            if (newPage.lastPage){
                
                // Get the last page of the Notes, 
                // Change the state of the loading state and turn on finished
                Notes.loadingState = NoteView.stateTexts.nomore;
                if (!Notes.finished) {
                    Notes.content = Notes.content.concat(newPage.content);
                }
                Notes.finished = true;
            } else {
                Notes.finished = false;
                Notes.loadingState = NoteView.stateTexts.more;
                Notes.content = Notes.content.concat(newPage.content);
                Notes.page ++;
            }
        });
};


/*
    Invoke this method to load comments for the first time
*/
NoteView.prototype.ShowComments = function(note){
    
    // Toggle comments
    note.showComments = !note.showComments;
    
    // Turning on the comments 
    if (note.showComments) {
        // Initlizate the note entity
        note.last = 0;
        note.page = 0;
        note.size = this.commentPageSize;
        note.finished = false;
        note.Comments = {content: []};

        // Get the content of the comments
        this.ShowMoreComments.call(this, note);
    }
}


/*
    Invoke this method to load more comments
*/
NoteView.prototype.ShowMoreComments = function (note){
    
    // Change the loading state to loading
    note.loadingState = this.stateTexts.loading;

    // Make the reference to the NoteView object
    var NoteView = this;

    // GET the Comments entity
    var newPage = this.Comment.get({by: "notes", id: note.id, page: note.page, size: note.size, sortBy: "id", direction: "DESC"}, function(){               
        if (newPage.lastPage){
            
            // Get the last page of the Comments, 
            // Change the state of the loading state and turn on finished
            note.loadingState = NoteView.stateTexts.nomore;
            if (!note.finished) {
                note.Comments.content = note.Comments.content.concat(newPage.content);
            }
            note.finished = true;
        } else {
            note.finished = false;
            note.loadingState = NoteView.stateTexts.more;
            note.Comments.content = note.Comments.content.concat(newPage.content);
            note.page ++;
        }
    });
};




/*
    Invoke this method to add a comment to a note
*/
NoteView.prototype.addComment = function(note, username){
    
    // Check the content of comment is avaliable
    var content = note.newCommentContent;
    if ( content === "" || content === undefined ) return;

    // Get the comments reference and set the comment entity
    var comments = note.Comments.content;
    var comment = {content: content};
        
    // POST the comment entity by Comment factory
    this.Comment.save({by: "notes", id: note.id}, comment);
    
    // Add creation time and username to the a temporary object 
    var append = { creationTime: '刚刚', username: username} ;

    // Add the comment entity to the top of Comments list
    comments.unshift( $.extend({}, comment, append) );

    // Reset the form 
    note.newCommentContent = "";

    // Add the count of comments
    note.commentCount ++;
}


/*
    Create the module for noteViewServices
*/
angular.module('noteViewServices', ['noteServices', 'commentServices']).
    factory('NoteView', ['Note', 'Comment', function (Note, Comment) {
        
        // Dependency injection
        NoteView.prototype.Note = Note;
        NoteView.prototype.Comment = Comment;
    
        // Return the NoteView object with
        // the reference to Note and Comment
        return NoteView;
    }]);