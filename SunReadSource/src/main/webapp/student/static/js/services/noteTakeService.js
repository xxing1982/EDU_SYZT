/* 
    Note view object 
*/
var NoteTake = function(bookDetails){
    
    // Destroy the old noteTake
    if (this.dropzone) {
        this.dropzone.destory();
    }
    
    // Initlizate the properties of details
    this.id = bookDetails.id;
    this.name = bookDetails.name;
    this.author = bookDetails.author;
    this.evaluationNum = bookDetails.evaluationNum;
    this.publisher = bookDetails.publisher;
    this.point = bookDetails.point;
    this.wordCount = bookDetails.wordCount;
    this.pageCount = bookDetails.pageCount;
    this.pictureUrl = bookDetails.pictureUrl;
    
    // Image uploader
    var noteTake = this;
    this.dropzone = this.Dropzone(this.config.NOTEPIC, function(url){
        noteTake.image = url;
    });
};

// The states of noteTake
NoteTake.prototype.states = {idle: '提交笔记', pending: '提交中...'};

/* 
    Note view methods 
*/
NoteTake.prototype.send = function(){
    
    // Create a note entity from input 
    var note = {title: this.title, content: this.content, image: this.image};

    // Make reference to noteTake
    var noteTake = this;
    noteTake.state = noteTake.states.pending;

    // POST the note by the note service with book id
    this.Note.save({by: "books", id: this.id}, note, function(){
        
        // Reset the model
        noteTake.title = undefined;
        noteTake.content = undefined;
        noteTake.image = undefined;
        noteTake.state = noteTake.states.idle;
        noteTake.dropzone.removeAllFiles();
        
        // Dismiss the modal
        $('#book-takenote-model').modal('hide');
        noteTake.$rootScope.modal = {
            title: '做笔记',
            content:'做笔记成功'
        };
        $('#alert-modal').modal();
    });
}



/*
    Create the module for noteTakeServices
*/
angular.module('noteTakeServices', ['noteServices']).
    factory('NoteTake', ['Note', '$rootScope','Dropzone', 'config', function(Note, $rootScope, Dropzone, config){
        
        // Dependency injection
        NoteTake.prototype.Note = Note;
        NoteTake.prototype.$rootScope = $rootScope;
        NoteTake.prototype.Dropzone = Dropzone;
        NoteTake.prototype.config = config;
        
        // Initlizate the noteTake
        NoteTake.prototype.state = NoteTake.prototype.states.idle;
        
        // Return the NoteTake object with
        // the reference to Note
        return NoteTake;
    }]);
