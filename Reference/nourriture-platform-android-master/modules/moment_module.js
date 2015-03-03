/**
 * Created by Pavel Prochazka on 23/10/14.
 * Module for moments specific API calls.
 */

var restify = require('restify');
var _ = require('lodash');
var async = require('async');

module.exports = function (server, models) {

    // Create - Moment
    server.post('/moment', function (req, res, next) {
        var newMoment = new models.Moment(req.body);

        newMoment.save(function (err) {
            if (!err) {
                res.send(newMoment);
                next();
            } else {
                if (err.name == "ValidationError") {
                    next(new restify.InvalidContentError(err.toString()));
                } else {
                    console.error("Failed to insert moment into database:", err);
                    next(new restify.InternalError("Failed to insert moment due to an unexpected internal error"));
                }
            }
        });
    });

    // Read - Moment
    server.get('/moment/:id', function(req, res, next) {
        models.Moment.findById(req.params.id, function(err, moment) {
            if(!err) {
                if(moment) {
                    res.send(moment);
                    next();
                } else {
                    next(new restify.ResourceNotFoundError("No moment found with the given id"));
                }
            } else {
                console.error("Failed to query database for specific moment:", err);
                next(new restify.InternalError("Failed to retrieve moment due to an unexpected internal error"));
            }
        });
    });

    // Reads (plural) - Moment (all or filtered by author)
    server.get('/moment', function(req, res, next) {
        // Construct basic query
        var query = models.Moment
            .find()
            .select({ comments:0, likes:0 });    // Don't select likes and comments for plural queries

        // If author parameter specified, use it to filter
        if(req.params.author) {
            query.find({ "author.cId": req.params.author });
        } else if(req.params.subjectID) {
            query.find({ subjectID: req.params.subjectID });
        } else if(req.params.followedBy) {
            momentsFollowedBy(req.params.followedBy, res, next);
            return; // Response composed and return in function below
        }

        query.sort({created:'descending'});

        // Execute query
        query.exec(function(err, moments) {
                if(!err) {
                    res.send(moments);
                    next();
                } else {
                    console.error("Failed to read moments from database", err);
                    next(new restify.InternalError("Failed to retrieve moments due to an unexpected internal error"));
                }
            });
    });

    // Utility method for GET /moments above (To keep the code for more basic queries clean)
    function momentsFollowedBy(id, res, next) {
        async.waterfall([
            // Retrieve IDs of consumers followed by the given consumer
            function(callback) {
                models.Consumer.findOne({ "_id":id }, { "following.cId": 1 }, function(err, consumer) {
                    if(!err) {
                        if(consumer) {
                            var followingIds = _.pluck(consumer.following, "cId");
                            followingIds.push(consumer._id);
                            callback(null, followingIds);
                        } else {
                            callback(new restify.ResourceNotFoundError("No users found with the given username"));
                        }
                    } else {
                        console.error("Failed to query database for consumer profile:", err);
                        callback(new restify.InternalError("Failed to retrieve moments due to an unexpected internal error"));
                    }
                });
            },
            // Retrieve moments authored by them
            function(followingIds, callback) {
                models.Moment.find({ "author.cId": { $in: followingIds }}, function(err, moments) {
                    if(!err) {
                        if(moments) {
                            callback(null, moments);
                        } else {
                            callback(null, []);
                        }
                    } else {
                        console.error("Failed to query database for consumer profile:", err);
                        callback(new restify.InternalError("Failed to retrieve moments due to an unexpected internal error"));
                    }
                });
            }
        ],
        // Respond with result or error
        function(err, result) {
            if(!err) {
                res.send(result);
                next();
            } else {
                next(err);
            }
        });
    }

    // Update - Moment
    server.put('/moment/:id', function(req, res, next) {
       next(new restify.NotAuthorizedError("Currently consumers are not authorized to update moment, not even their own, sorry."));
    });

    // Delete - Moment
    server.del('/moment/:id', function(req, res, next) {
        models.Moment.findOneAndRemove({ "_id":req.params.id }, function(err, deletedMoment) {
            if(!err) {
                if(deletedMoment) {
                    res.send(deletedMoment);
                    next();
                } else {
                    next(new restify.ResourceNotFoundError("No moment found with the given id"));
                }
            } else {
                console.error("Failed to delete moment from database:", err);
                next(new restify.InternalError("Failed to delete moment due to an unexpected internal error"));
            }
        });
    });

    // Create - Comment (to moment)
    server.post('/moment/:id/comment', function (req, res, next) {
        // Validate comment and add created time
        var newComment = new models.Comment(req.body);
        newComment.created = new Date;
        newComment.validate(function(err) {
            if(!err) {
                // Do actual insertion
                models.Moment.findOneAndUpdate({ "_id":req.params.id}, { "$push": { "comments": newComment._doc }, "$inc": { "commentCount": 1 } }, function(err) {
                    if(!err) {
                        res.send(newComment);
                        next();
                    } else {
                        console.error("Failed to update moment in database:", err);
                        next(new restify.InternalError("Failed to insert comment due to an unexpected internal error"))
                    }
                });
            } else {
                next(new restify.InvalidContentError(err.toString()));
            }
        });
    });

    // Delete - Comment (from moment)
    server.del('/moment/:id/comment/:cid', function (req, res, next) {
        models.Moment.findOneAndUpdate({ "_id":req.params.id}, { "$pull": { "comments": { "_id": req.params.cid } }, "$inc": { "commentCount": -1 } }, { "new": false }, function(err, moment) {
            if(!err) {
                if(moment) {
                    // Look up comment in unmodified moment
                    var comment = _.find(moment.toObject().comments, function(item) {
                        return item._id.toString() == req.params.cid;
                    });

                    // If it was found return it, otherwise it never existed and 404 should be returned
                    if(comment) {
                        res.send(comment);
                        next();
                    } else {
                        next(new restify.ResourceNotFoundError("No comment found with the given id in the given moment"));
                    }
                } else {
                    next(new restify.ResourceNotFoundError("No moment found with the given id"));
                }
            } else {
                console.error("Failed to query database for specific moment:", err);
                next(new restify.InternalError("Failed to insert comment due to an unexpected internal error"));
            }
        });
    });

    // Create - Like (to moment)
    server.post('/moment/:id/like', function (req, res, next) {
         // Validate like
        var newLike = new models.Like(req.body);
        newLike.validate(function(err) {
            if(!err) {
                // Do actual insertion
                models.Moment.findOneAndUpdate({ "_id":req.params.id}, { "$addToSet": { "likes": newLike.toObject() }, "$inc": { "likeCount": 1 } }, function(err, moment) {
                    if(!err) {
                        if(moment) {
                            res.send(newLike);
                            next();
                        } else {
                            next(new restify.ResourceNotFoundError("No moment found with the given id"));
                        }
                    } else {
                        console.error("Failed to update moment in database:", err);
                        next(new restify.InternalError("Failed to insert like due to an unexpected internal error"))
                    }
                });
            } else {
                next(new restify.InvalidContentError(err.toString()));
            }
        });
    });

    // Delete - Like (from moment)
    server.del('/moment/:id/like/:cid', function (req, res, next) {
        models.Moment.findOneAndUpdate({ "_id":req.params.id}, { "$pull": { "likes": { "cId": req.params.cid } }, "$inc": { "likeCount": -1 } }, { "new": false }, function(err, moment) {
            if(!err) {
                if(moment) {
                    // Look up like in unmodified moment
                    var like = _.find(moment.toObject().likes, function(item) {
                        return item.cId.toString() == req.params.cid;
                    });

                    // If it was found return it, otherwise it never existed and 404 should be returned
                    if(like) {
                        res.send(like);
                        next();
                    } else {
                        next(new restify.ResourceNotFoundError("No like found from the given user in the given moment"));
                    }
                } else {
                    next(new restify.ResourceNotFoundError("No moment found with the given id"));
                }
            } else {
                console.error("Failed to query database for specific moment:", err);
                next(new restify.InternalError("Failed to remove like due to an unexpected internal error"));
            }
        });
    });
};