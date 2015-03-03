/**
 * Created by Pavel Prochazka on 22/10/14.
 * Module for all gastronomist specific API calls.
 */
var restify = require('restify');

module.exports = function (server, models) {

    // Create - Gastronomist profile
    server.post('/gastronomist', function (req, res, next) {
        var newGastronomist = new models.Gastronomist(req.body);

        newGastronomist.save(function (err) {
            if (!err) {
                res.send(newGastronomist);
                next();
            } else {
                if (err.name == "ValidationError") {
                    next(new restify.InvalidContentError(err.toString()));
                } else {
                    console.error("Failed to insert gastronomist into database:", err);
                    next(new restify.InternalError("Failed to insert user due to an unexpected internal error"));
                }
            }
        });
    }); //WORKS!

    // Read - Gastronomist profile
    server.get('/gastronomist/:username', function (req, res, next) {
        models.Gastronomist.findOne({username: req.params.username}, function (err, gastronomist) {
            if (!err) {
                if (gastronomist) {
                    res.send(gastronomist);
                    next();
                } else {
                    next(new restify.ResourceNotFoundError("No gastronomist found with the given username"));
                }
            } else {
                console.error("Failed to query database for gastronomist profile:", err);
                next(new restify.InternalError("Failed to insert gastronomist due to an unexpected internal error"));
            }
        });
    }); //WORKS!

    // Read all gastronomist
    server.get('/gastronomist', function (req, res, next) {
        console.log('Read all gastronomist requested');

        models.Gastronomist.find(function (err, gastronomists) {
            if (!err) {
                res.send(gastronomists);
                next();
            } else {
                console.error("Failed to read gastronomists from database:", err);
                next(new restify.InternalError("Failed to read gastronomists due to an unexpected internal error"));
            }
        });
    }); //WORKS!

    // Update - Gastronomist profile
    server.put('/gastronomist/:username', function (req, res, next) {
        // Retrieve existing gastronomist, overwrite fields, validate and save
        models.Gastronomist.find({username: req.params.username}, function (err, result) {
            if (!err) {
                if (result.length != 0) {
                    var gastronomist = result[0];

                    // Overwrite fields with value from request body
                    for (var key in req.body) {
                        gastronomist[key] = req.body[key];
                    }

                    // Validate and save
                    gastronomist.save(function (err) {
                        if (!err) {
                            res.send(gastronomist);
                            next();
                        } else {
                            if (err.name == "ValidationError") {
                                next(new restify.InvalidContentError(err.toString()));
                            } else {
                                console.error("Failed to insert gastronomist into database:", err);
                                next(new restify.InternalError("Failed to insert gastronomist due to an unexpected internal error"));
                            }
                        }
                    });
                } else {
                    // No user found with given username
                    next(new restify.ResourceNotFoundError("No gastronomist found with the given username"));
                }
            } else {
                // Database connection error
                console.error("Failed to query database for gastronomist profile:", err);
                next(new restify.InternalError("Failed to insert user due to an unexpected internal error"));
            }
        });
    }); //WORKS!

    // Delete - gastronomist profile
    server.del('/gastronomist/:username', function (req, res, next) {
        models.Gastronomist.findOneAndRemove({username: req.params.username}, function (err, deletedGastronomist) {
            if (!err) {
                if (deletedGastronomist) {
                    res.send(deletedGastronomist);
                    next();
                } else {
                    next(new restify.ResourceNotFoundError("No gastronomist found with the given username"));
                }
            } else {
                console.error("Failed to delete gastronomist profile from database:", err);
                next(new restify.InternalError("Failed to delete gastronomist due to an unexpected internal error"));
            }
        });
    }); //WORKS!
}

