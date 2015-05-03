/*
    Create the module for editableServices
*/
angular.module('editableServices', []).
    factory('Editable', function () {

        /* 
            Note view object 
        */
        var Editable = function (content) {
            this.content = content;
        };


        /*
            Some constants 
        */
        Editable.prototype.cached = new Object();
        Editable.prototype.editing = false;

        /*
            Invoke this method to load more notes
        */
        Editable.prototype.toggleEdit = function(save, callback){

            // save === true means click on "Save"
            if (save) {

                // Clone entity
                for (var key in this.cached){
                    this.content[key] = this.cached[key];
                }

                // The callback to save the entity
                if (typeof callback === 'function') { callback(); } 
            } else {

                // Create cached entity
                this.cached = new Object();

                // Clone entity
                for (var key in this.content){
                    this.cached[key] = this.content[key];
                }
            }

            // Inverse the state of editing
            this.editing = !this.editing ;
        };

        /*
            Invoke this method to load more notes
        */
        
        // Return the Editable object with
        // the reference to Note and Comment
        return Editable;
    });