/**
 * Created by Pavel Prochazka on 23/10/14.
 * Module for consumer specific API calls.
 */

var fs = require('fs');
var restify = require('restify');
var _ = require('lodash');
var async = require('async');

module.exports = function (server, models, aws) {

    // Create - Consumer profile
    server.post('/consumer', function(req, res, next) {
        var newConsumer = new models.Consumer(req.body);

        newConsumer.save(function (err) {
           if(!err) {
               res.send(newConsumer);
               next();
           } else {
               if(err.name == "ValidationError") {
                   next(new restify.InvalidContentError(err.toString()));
               } else {
                   console.error("Failed to insert consumer into database:", err);
                   next(new restify.InternalError("Failed to insert user due to an unexpected internal error"));
               }
           }
        });
    });

    // Read - Consumer profile
    server.get('/consumer/:username', function(req, res, next) {
        models.Consumer.findOne({ username:req.params.username }, function(err, consumer) {
            if(!err) {
                if(consumer) {
                    res.send(consumer);
                    next();
                } else {
                    next(new restify.ResourceNotFoundError("No users found with the given username"));
                }
            } else {
                console.error("Failed to query database for consumer profile:", err);
                next(new restify.InternalError("Failed to insert user due to an unexpected internal error"));
            }
        });
    });

    // Update - Consumer profile
    server.put('/consumer/:username', function(req, res, next) {
        // Retrieve existing user, overwrite fields, validate and save
        models.Consumer.find({ username:req.params.username }, function(err, result) {
            if(!err) {
                if(result.length != 0) {
                    var consumer = result[0];

                    // Overwrite fields with value from request body
                    for (var key in req.body) {
                        consumer[key] = req.body[key];
                    }

                    // Validate and save
                    consumer.save(function (err) {
                        if(!err) {
                            res.send(consumer);
                            next();
                        } else {
                            if(err.name == "ValidationError") {
                                next(new restify.InvalidContentError(err.toString()));
                            } else {
                                console.error("Failed to insert consumer into database:", err);
                                next(new restify.InternalError("Failed to insert user due to an unexpected internal error"));
                            }
                        }
                    });
                } else {
                    // No user found with given username
                    next(new restify.ResourceNotFoundError("No users found with the given username"));
                }
            } else {
                // Database connection error
                console.error("Failed to query database for consumer profile:", err);
                next(new restify.InternalError("Failed to insert user due to an unexpected internal error"));
            }
        });
    });

    // Delete - Consumer profile
    server.del('/consumer/:username', function(req, res, next) {
        models.Consumer.findOneAndRemove({ username:req.params.username }, function(err, deletedConsumer) {
            if(!err) {
                if(deletedConsumer) {
                    res.send(deletedConsumer);
                    next();
                } else {
                    next(new restify.ResourceNotFoundError("No user found with the given username"));
                }
            } else {
                console.error("Failed to delete consumer profile from database:", err);
                next(new restify.InternalError("Failed to delete consumer due to an unexpected internal error"));
            }
        });
    });

    // Reads (plural) - Consumer profile
    server.get('/consumer/', function(req, res, next) {
        models.Consumer.find(function(err, consumers) {
            if(!err) {
                res.send(consumers);
                next();
            } else {
                console.error("Failed to read consumers from database:", err);
                next(new restify.InternalError("Failed to insert user due to an unexpected internal error"));
            }
        });
    });

    // Read - Follow relations of a specific consumer
    server.get("/consumer/:username/following", function (req, res, next) {
        models.Consumer.findOne({ username:req.params.username }, { "following":1 }, function(err, consumer) {
            if(!err) {
                if(consumer) {
                    res.send(consumer.following);
                    next();
                } else {
                    next(new restify.ResourceNotFoundError("No consumer found with the given username"));
                }
            } else {
                console.error("Failed to query database for consumer profile:", err);
                next(new restify.InternalError("Failed to retrieve follow data due to an unexpected internal error"));
            }
        });
    });

    // Create - Following relation from this consumer to another consumer
    server.post("/consumer/:id/following", function (req, res, next) {
        async.waterfall([
            // Retrieve consumer to be followed and compose a follow relation
            function(callback) {
                models.Consumer.findOne(
                    { "_id": req.body },        // Selection
                    { name: 1, picture: 1, occupation: 1 },    // Projection
                    function(err, consumer) {   // Callback
                        if(!err) {
                            if(consumer) {
                                var newFollowRelation = {
                                    created: new Date(),
                                    cId: consumer.id,
                                    name: consumer.name,
                                    picture: consumer.picture,
                                    occupation: consumer.occupation
                                };
                                callback(null, newFollowRelation);
                            } else {
                                callback(new restify.ResourceNotFoundError("The consumer attempted to follow was not found"));
                            }
                        } else {
                            console.error("Failed to retrieve consumer from database:", err);
                            callback(new restify.InternalError("Failed to insert follow relation due to an unexpected internal error"))
                        }
                    }
                );
            },
            // Add follow relation to consumers following set
            function(newFollowRelation, callback) {
                models.Consumer.findOneAndUpdate(
                    { "_id":req.params.id },                                // Selection
                    { "$addToSet": { "following": newFollowRelation } },    // Update
                    function(err, consumer) {                               // Callback
                        if(!err) {
                            if(consumer) {
                                callback(null, newFollowRelation);
                            } else {
                                callback(new restify.ResourceNotFoundError("No consumer found with the given id"));
                            }
                        } else {
                            console.error("Failed to update consumer in database:", err);
                            callback(new restify.InternalError("Failed to insert follow relation due to an unexpected internal error"))
                        }
                    }
                );
            }
        ], function(err, newFollowRelation) {
            if(!err) {
                res.send(newFollowRelation);
                next();
            } else {
                next(err);
            }
        });
    });

    // Delete - Following relation from this consumer to another consumer
    server.del('/consumer/:id/following/:target', function(req, res, next) {
        models.Consumer.findOneAndUpdate(
            { "_id":req.params.id },                         // Selection
            { "$pull": { "following": { "cId": req.params.target } } }, // Update
            { "new": false },                                           // Options
            function(err, consumer) {                                   // Callback
                if(!err) {
                    if(consumer) {
                        // Look up relation in unmodified consumer (NOTE: due to the {new:false} option above we receive the unmodified consumer)
                        var followRelation = _.find(consumer.toObject().following, function(item) {
                            return item.cId.toString() == req.params.target;
                        });

                        // If it was found return it, otherwise it never existed and 404 should be returned
                        if(followRelation) {
                            res.send(followRelation);
                            next();
                        } else {
                            next(new restify.ResourceNotFoundError("The consumer was already not following this other consumer"));
                        }
                    } else {
                        next(new restify.ResourceNotFoundError("No consumer found with the given username"));
                    }
                } else {
                    console.error("Failed to query database for modifying a specfici consumer:", err);
                    next(new restify.InternalError("Failed to delete follow relation due to an unexpected internal error"));
                }
            }
        );
    });

    // Create - Consumer picture
    server.post('/consumer/:username/picture', function(req, res, next) {
        for (var filename in req.files) {
            if(filename != "_proto_") {
                var file = req.files[filename];
                var filetype = /^.+\.(.+)$/.exec(filename)[1];
                var typeOK = _.contains(["jpg", "jpeg", "png", "gif"], filetype);
                var newFileName = req.params.username + "." + filetype;
                if(typeOK && file.size < 1000000) {
                    fs.readFile(file.path, { flag: "r" }, function (err, data) {
                        if (!err) {
                            s3 = new aws.S3();
                            s3.putObject({
                                    Bucket: "nourriture-consumer",
                                    ACL: "public-read",
                                    Key: newFileName,
                                    ContentType: "image/" + filetype,
                                    Body: data
                                },
                                function (err, data) {
                                    if (!err) {
                                        next();
                                    } else {
                                        console.error("Failed to put consumer picture into S3", err);
                                        next(new restify.InternalError("Failed to save picture due to an unexpected internal error"));
                                    }
                                }
                            );
                        } else {
                            console.error("Failed to open temporary file created by consumer picture upload");
                            next(new restify.InternalError("Failed to save picture due to an unexpected internal error"));
                        }
                    });
                }
                console.log(file.path);
            }
        }
    });
};

