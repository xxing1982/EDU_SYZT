/*
*/
var Loadable = function(){
}

Loadable.prototype.page= 1;
Loadable.prototype.size = 9;
Loadable.prototype.arguments = {};
Loadable.prototype.entities = new Object();
Loadable.prototype.loadingState = "加载更多";
 
Loadable.stateTexts = { more : "加载更多", loading: "更多加载中...", nomore: "没有了"};

// The build method to initlizate the pageable object
Loadable.prototype.build = function(Entity,page,size){

    // The resource and the entities
    this.Entity = Entity;

    // Initlizate the entities
    this.entities = {content: new Array(0)};
    
    this.page = page;
    
    this.size = size;

}

Loadable.prototype.ShowMore = function (arguments){
    
    // Make the reference to the Notes object
    var page = this.page;
    var size = this.size;

//    Make the reference to the NoteView object
//    var NoteView = this;

    // GET the Notes entity
    var newPage = this.Entity.get(
        $.extend({}, {size: this.size, page: page - 1}, this.arguments), function(){ 
           
            /*
                Check the lastPage flag and set stateTexts
            */
            if (newPage.lastPage){
                
                // Get the last page of the Notes, 
                // Change the state of the loading state and turn on finished
                this.loadingState = this.stateTexts.nomore;
            } else {
                this.loadingState = this.stateTexts.more;
                this.page ++;
            }
        });
};

angular.module('loadableServices', []).
    factory('Loadable', function () {

        // Dependency injection
        return Loadable;
    });