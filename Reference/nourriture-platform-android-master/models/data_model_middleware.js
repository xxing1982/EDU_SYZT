/**
 * Created by niels on 10/30/14.
 * Various utility middleware (validators, post/pre procedures) for database interaction
 */

// Custom length validator for strings
exports.strLength = function(max) {
    return [
        function(v) {
            if(v) {
                return v.length < max;
            }
            return true;
        },
            "Only " + max + " characters are allowed"
    ];
};

// middleware for automatically updating timestamps ("created" and "modified")
exports.updateTimeStamps = function(next, done) {
    if (!this.created) this.created = new Date;
    this.modified = new Date;
    next(); done();
};

// middleware for automatically initializing moment like/comment counters
exports.initMomentCounters = function(next, done) {
    if(!this.likeCount) this.likeCount = 0;
    if(!this.commentCount) this.commentCount = 0;
    next(); done();
};