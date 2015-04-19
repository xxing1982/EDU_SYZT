/*
*/
var Loadable = function(){
    
}

Loadable.prototype.page= 1;
Loadable.prototype.size = 9;
Loadable.prototype.arguments = {};
Loadable.prototype.entities = new Object();
Loadable.prototype.loadingState = "加载更多";
 
Loadable.prototype.stateTexts = { more : "加载更多", loading: "更多加载中...", nomore: "没有了"};

// The build method to initlizate the pageable object
Loadable.prototype.build = function(Entity){

    // The resource and the entities
    this.Entity = Entity;

    // Initlizate the entities
    this.entities = {content: new Array()};

}

Loadable.prototype.ShowMore = function (arguments,page,size){
    
    var stateTexts = this.stateTexts;
//    
//    var entities = this.entities;
    var newPage = this.Entity.get(
        $.extend({}, {page: page - 1,size: this.size}, this.arguments), function(data){ 

            /*
                Check the lastPage flag and set stateTexts
            */
            
            if (newPage.lastPage){
                
                // Get the last page of the Notes, 
                // Change the state of the loading state and turn on finished
                this.loadingState = stateTexts.nomore;
                console.log(this.loadingState);
            } else {
                this.loadingState = stateTexts.more;
//                this.entities.content
                this.page ++;
            }
           this.entities = newPage;
        });
    console.log(newPage);


    console.log(this.entities.content);
};

angular.module('loadableServices', []).
    factory('Loadable', function () {

        // Dependency injection
        return Loadable;
    });