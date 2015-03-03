/**
 * Created by Pavel Prochazka on 22/10/14.
 * Module for all company specific API calls.
 */

var restify = require('restify');
var fs = require('fs');
var async = require('async');
//var _ = require('lodash'); TODO: what for hm?

module.exports = function (server, models) { ///passing mongoose object to constructor (this anonymous method)

    //Create a company
    server.post('/company', function(req, res, next) {
        console.log('Create company requested');

        var newCompany = new models.Company(req.body);  //req.body has to be POSTed in JSON format!!!

        newCompany.save(function (err) {
            if(!err) {
                res.send(newCompany);
                next();
            } else {
                if(err.name == "ValidationError") {
                    next(new restify.InvalidContentError(err.toString()));  //Restify takes care of HTTP error handling
                } else {
                    console.error("Failed to insert company into database:", err);
                    next(new restify.InternalError("Failed to insert company due to an unexpected internal error"));    //Restify takes care of HTTP error handling
                }
            }
        });
    });

    //Read a company based on username
    server.get('/company/:username', function (req, res, next) {
        console.log('Read company requested');

        models.Company.findOne({ username:req.params.username }, function(err, company) {
            if(!err) {
                if(company) {
                    res.send(company);
                    next();
                } else {
                    next(new restify.ResourceNotFoundError("No company found with the given company username"));
                }
            } else {
                console.error("Failed to query database for company profile:", err);
                next(new restify.InternalError("Failed to read company due to an unexpected internal error"));
            }
        });
    });

    //Read all companies
    server.get('/company', function (req, res, next) {
        console.log('Read all companies requested');

        async.waterfall([
                function (done) {
                    var searchterm = req.params["search"];
                    if(searchterm) {
                        models.Company.find({ "name": req.params["search"] }, done);
                    } else {
                        models.Company.find(done);
                    }
                }
            ],
            function (err, companies) {
                if(!err) {
                    res.send(companies);
                    next();
                } else {
                    console.error("Failed to read companies from database:", err);
                    next(new restify.InternalError("Failed to read companies due to an unexpected internal error"));
                }
            }
        );
    });

    //Update a company
    server.put('/company/:username', function (req, res, next) {
        console.log('Update company requested');

        // Retrieve existing company, overwrite fields, validate and save
        models.Company.find({ username:req.params.username }, function(err, result) {
            if(!err) {
                if(result.length != 0) {
                    var company = result[0];    // Get the first founded company

                    // Overwrite fields with value from request body
                    for (var key in req.body) {     //req.body has to be POSTed in JSON format!!!
                        company[key] = req.body[key];
                    }

                    // Validate and save
                    company.save(function (err) {
                        if(!err) {
                            res.send(company);
                            next();
                        } else {
                            if(err.name == "ValidationError") {
                                next(new restify.InvalidContentError(err.toString()));
                            } else {
                                console.error("Failed to insert company into database:", err);
                                next(new restify.InternalError("Failed to update company due to an unexpected internal error"));
                            }
                        }
                    });
                } else {
                    // No company found with given username
                    next(new restify.ResourceNotFoundError("No company found with the given username"));
                }
            } else {
                // Database connection error
                console.error("Failed to query database for company profile:", err);
                next(new restify.InternalError("Failed to update company due to an unexpected internal error"));
            }
        });
    });

    //Delete a company
    server.del('/company/:username', function (req, res, next) {
        console.log('Delete company requested');

        models.Company.findOneAndRemove({ username:req.params.username }, function(err, deletedCompany) {
            if(!err) {
                if(deletedCompany) {
                    res.send(deletedCompany);
                    next();
                } else {
                    next(new restify.ResourceNotFoundError("No company found with the given username"));
                }
            } else {
                console.error("Failed to delete company profile from database:", err);
                next(new restify.InternalError("Failed to delete company due to an unexpected internal error"));
            }
        });
    });
};