/*
    Sortable object work with sortable table
    
*/    
{
    var Sortable = function(sortBy, direction, callback){

        // Initlizate the default sort parameter
        this.sortBy = sortBy;
        this.direction = direction;
        this.callback = callback;
    }

    // The sort function
    Sortable.prototype.sort = function(sortBy){
        if ( this.sortBy !== sortBy ) {
            this.direction = 'DESC';
        } else {
            this.direction = this.direction === 'DESC' ? 'ASC' : 'DESC';
        }
        this.sortBy = sortBy;
        if (this.parameter) {
            this.callback(this.parameter, this.sortBy, this.direction);
        }
    }

    angular.module('sortableServices', []).
        factory('Sortable', function () {

            // Dependency injection
            return Sortable;
        });
}