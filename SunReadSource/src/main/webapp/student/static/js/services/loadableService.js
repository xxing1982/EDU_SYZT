/*
    Loadable object work with load more buttom and
    Spring pageable
*/
var Loadable =function(){};

/*
    The properites to of Loadable
*/
Loadable.prototype.page = 0;
Loadable.prototype.size = 9;
Loadable.prototype.finished = false;
Loadable.prototype.entities = new Object();
Loadable.prototype.stateTexts = { more : "加载更多", loading: "更多加载中...", nomore: "没有了"};
Loadable.prototype.loadingState = Loadable.prototype.stateTexts.more;
Loadable.prototype.arguments = {};

Loadable.prototype.part = new Array();
Loadable.prototype.all = new Array();

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

    // Make the reference to the loadable
    var loadable = this;

    // Change the state of the loading state
    loadable.loadingState = stateTexts.loading;
    
    // Make the reference to the callback
    if (typeof callback === 'function') {
        this.callback = callback; 
    }
    var getCallback = this.callback;
    
    // Get the entities of new page
    var newPage = this.Entity.get(
        $.extend({}, {page: this.page, size: this.size}, this.arguments), function(){

            /*
                Check the lastPage flag and set stateTexts
            */
            if (newPage.lastPage){

                // Get the last page of the Notes,
                // Change the state of the loading state and turn on finished
                loadable.loadingState = stateTexts.nomore;
                if (!loadable.finished || entities.content.length === 0) {
                    entities.content = entities.content.concat(newPage.content);
                }
                loadable.finished = true;

            } else {
                loadable.finished = false;
                loadable.loadingState = stateTexts.more;
                entities.content = entities.content.concat(newPage.content);
                loadable.page ++;
            }
            
            // Invoke the callback
            if (getCallback && typeof getCallback === 'function') { getCallback(); };
        });
};


angular.module('loadableServices', []).
    factory('Loadable', function () {

        // Dependency injection
        return Loadable;
    });
