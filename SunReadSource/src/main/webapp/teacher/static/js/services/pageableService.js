/*
    Pageable object work with page number, page nubmer input and
    Spring pageable
*/
var Pageable = function () {
};


/*
    The properites to of pageable
*/
Pageable.prototype.page = 1;
Pageable.prototype.size = 5;
Pageable.prototype.arguments = {};
Pageable.prototype.pageNumbers = { startPage: 1,
                                   content: new Array(8) };

Pageable.prototype.entities = new Object();
Pageable.prototype.placeHolders = { placeHoldersElement: " " }


// The build method to initlizate the pageable object
Pageable.prototype.build = function(Entity){

    // The resource and the entities
    this.Entity = Entity;

    // Update the page number
    this.pageNumbers.update(this.pageNumbers.startPage, this.pageNumbers.content.length);

    // Initlizate the entities
    this.entities = {content: new Array(0)};

    // Update the placeholders
    this.placeHolders.update(this.size);
}


// Update page number entities
Pageable.prototype.pageNumbers.update = function(startPage, count){
    this.content = new Array(count);
    for (var i = 0; i < this.content.length; i ++){
        this.content[i] = startPage + i;
    }
    this.startPage = startPage;
}

// Fill placeholder content with placeholders elements
Pageable.prototype.placeHolders.update = function(count){
    this.content = new Array(0);
    for( var i = 0; i < count; i ++ ){
        this.content.push( $.extend(true, {}, this.placeHoldersElement) );
    }
}

// Get entities by page
Pageable.prototype.showPage = function(page, callback){

    // Check the page
    if (typeof page === 'number' && page > 0){

        // Make reference to this.entities and placeholder
        var entities = this.entities;
        var placeHolders = this.placeHolders;
        var size = this.size;
        var pageNumbers = this.pageNumbers;
        var pageable = this;

        // Get entities
        this.Entity.get($.extend({}, {size: this.size, page: page - 1}, this.arguments), function(data){
            // Check the bounds of page
            if (page > data.totalPages) {
                pageable.showPage(data.totalPages);
            } else {
                // Update the page of the pageable object
                pageable.page = page;

                // Copy the content of data to entities
                entities.content = data.content.splice(0);

                // Update the placeholders
                placeHolders.update(size - entities.content.length);

                // Update the page number
                var startPage = pageNumbers.startPage;
                var length = pageNumbers.content.length;
                var endPage = pageNumbers.startPage + length - 1;

                // Page is at the right side of page numbers
                if ( endPage <= page ){
                    pageNumbers.update( page - length + 1 + (page !== data.totalPages), Math.min(data.totalPages, length));
                }

                // Page is at the left side of page numbers
                else if ( page <= startPage){
                    pageNumbers.update( page - 1 +  (page === 1), Math.min(data.totalPages, length));
                }
            }

            // Call the callback
            if (callback !== undefined) { callback(); }

        },function(error){
          alert(error);
        });
    }
};


/*
    Create the module for pageableServices
*/
angular.module('pageableServices', []).
    factory('Pageable', function () {

        // Dependency injection
        return Pageable;
    });
