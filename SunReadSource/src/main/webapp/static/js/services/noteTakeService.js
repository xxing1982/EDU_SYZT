/* 
    Note view object 
*/
var NoteTake = function(bookDetails){
    
    // Initlizate the properties of details
    this.id = bookDetails.id;
    this.name = bookDetails.name;
    this.author = bookDetails.author;
    this.publisher = bookDetails.publisher;
    this.point = bookDetails.point;
    this.wordCount = bookDetails.wordCount;
    this.pageCount = bookDetails.pageCount;
};


/* 
    Note view methods 
*/
NoteTake.prototype.send = function(){

    // Create a note entity from input 
    var note = {title: this.title, content: this.content, image: this.image};

    // POST the note by the note service with book id
    this.Note.save({by: "books", id: this.id}, note);
};


/*
    Create the module for noteTakeServices
*/
angular.module('noteTakeServices', ['noteServices']).
    factory('NoteTake', ['Note', function(Note){
        
        // Dependency injection
        NoteTake.prototype.Note = Note;
    
        // Return the NoteTake object with
        // the reference to Note
        return NoteTake;
    }]);
