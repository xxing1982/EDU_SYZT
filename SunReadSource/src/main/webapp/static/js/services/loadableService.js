/*
    Loadable object work with load more buttom and
    Spring pageable
*/
var Loadable = function(){

}

/*
    The properites to of pageable
*/
Loadable.prototype.page = 0;
Loadable.prototype.size = 9;
Loadable.prototype.finished = false;
Loadable.prototype.entities = new Object();
Loadable.prototype.stateTexts = { more : "加载更多", loading: "更多加载中...", nomore: "没有了"};
Loadable.prototype.loadingState = Loadable.prototype.stateTexts.more;
Loadable.prototype.arguments = {};

// The build method to initlizate the loadable object
Loadable.prototype.build = function(Entity){

    // The resource and the entities
    this.Entity = Entity;

    // Initlizate the entities
    this.entities = { content: new Array() };

}

Loadable.prototype.get = function (callback){

    // Make the reference to the entities
    var entities = this.entities;
    
    // Make the reference to the stateTexts
    var stateTexts = this.stateTexts;
    
    // Make the reference to the loadingState
    var loadingState = this.loadingState;
    
    // Make the reference to the finished
    var finished = this.finished;

    // Make the reference to the finished
    var page = this.page;
    
    // Get the entities of new page
    var newPage = this.Entity.get(
        $.extend({}, {page: this.page, size: this.size}, this.arguments), function(){

            /*
                Check the lastPage flag and set stateTexts
            */
            if (newPage.lastPage){

                // Get the last page of the Notes,
                // Change the state of the loading state and turn on finished
                loadingState = stateTexts.nomore;
                if (!this.finished) {
                    entities.content = entities.content.concat(newPage.content);
                }
                finished = true;
                
            } else {
                finished = false;
                loadingState = stateTexts.more;
                entities.content = entities.content.concat(newPage.content);
                page ++;
            }
        });
    
    // Invoke the callback
    if (callback && typeof callback === 'function') { callback(); };
};

angular.module('loadableServices', []).
    factory('Loadable', function () {

        // Dependency injection
        return Loadable;
    });
