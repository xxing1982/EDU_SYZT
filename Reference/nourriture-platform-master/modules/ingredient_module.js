/**
 * Created by Jeremy Barthelemy on 24/10/14.
 * Module for all ingredient/product specific API calls.
 */

var restify = require('restify');

module.exports = function (server, models) {

    server.post('/ingredient', function (req, res, next) {

        console.log('Create ingredient requested');
        var newIngredient = new models.Ingredient(req.body);
        newIngredient.save(function (err) {
            if (!err) {
                res.send(newIngredient);
                next();
            }
            else {
                if(err.name == "ValidationError") {
                    next(new restify.InvalidContentError(err.toString()));
                }
                else {
                   console.error("Failed to insert ingredient into database:", err);
                   next(new restify.InternalError("Failed to insert Ingredient due to an unexpected internal error"));
                }
            }
        });
    }); //WORKS!

    server.put('/ingredient/:id', function (req, res, next) {
        if (!req.body) {
            next(new restify.InvalidContentError("No ingredient submitted for update"));
            return;
        }

        console.log('Update ingredient requested');
        models.Ingredient.find({ _id:req.params.id }, function (err, result) {
            if(!err) {
                if(result.length != 0) {
                    var ingredient = result[0];
                    for(var key in req.body) {
                        ingredient[key] = req.body[key];
                    }

                    ingredient.save(function (err) {
                        if (!err) {
                            res.send(ingredient);
                            next();
                        }
                        else {
                            if(err.name == "ValidationError") {
                                next(new restify.InvalidContentError(err.toString()));
                            }
                            else {
                                console.error("Failed to update ingredient into database:", err);
                                next(new restify.InternalError("Failed to update ingredient due to an unexpected internal error"));
                            }
                        }
                    });
                }
                else {
                    next(new restify.ResourceNotFoundError("No ingredient found with the given name"));
                }
            }
            else {
                console.error("Failed to query database for ingredient profile:", err);
                next(new restify.InternalError("Failed to update ingredient due to an unexpected internal error"));
            }
        });
    }); //WORKS!

    server.del('/ingredient/:id', function (req, res, next) {
        console.log('Delete ingredient requested');
        models.Ingredient.findOneAndRemove({ _id:req.params.id }, function (err, deletedIngredient) {
            if(!err) {
                if(deletedIngredient) {
                    res.send(deletedIngredient);
                    next();
                }
                else {
                    next(new restify.ResourceNotFoundError("No ingredient found with the given name"));
                }
            }
            else {
                console.error("Failed to delete ingredient profile from database:", err);
                next(new restify.InternalError("Failed to delete ingredients due to an unexpected internal error"));
            }
        });
    }); //WORKS!

    server.get('/ingredient', function (req, res, next) {
        console.log('Select all ingredients requested');
        query = models.Ingredient.find();

        if(req.params.company) {
            query.find({ company:req.params.company });
        } else if(req.params.name) {
            regex = new RegExp(req.params.name, "i");
            query.find( { name: regex } );
        }

        query.exec(function (err, ingredients) {
            if (!err) {
                res.send(ingredients);
                next();
            }
            else {
                console.error("Failed to read ingredients from database:", err);
                next(new restify.InternalError("Failed to read ingredients due to an unexpected internal error"));
            }
        });
    }); //WORKS!

    server.get('/ingredient/:id', function (req, res, next) {
        console.log('Select ingredient id requested');
        models.Ingredient.findOne({ _id:req.params.id }, function (err, ingredient) {
            if (!err) {
                if (ingredient.length != 0) {
                    res.send(ingredient);
                    next();
                }
                else {
                    next(new restify.ResourceNotFoundError("No ingredient found with the given id"));
                }
            }
            else {
                console.error("Failed to query database for ingredient profile:", err);
                next(new restify.InternalError("Failed to read ingredient due to an unexpected internal error"));
            }
        });
    }); //WORKS!

    server.get('/ingredient/name/:name', function (req, res, next) {
        console.log('Select ingredient name requested');
        models.Ingredient.find({name:req.params.name }, function (err, ingredient) {
            if (!err) {
                if (ingredient.length != 0) {
                    res.send(ingredient);
                    next();
                }
                else {
                    next(new restify.ResourceNotFoundError("No ingredient found with the given name"));
                }
            }
            else {
                console.error("Failed to query database for ingredient profile:", err);
                next(new restify.InternalError("Failed to read ingredient due to an unexpected internal error"));
            }
        });
    }); //WORKS!

    server.get('/ingredient/company/:username', function (req, res, next) {
        console.log('Select all ingredients company requested');
        models.Company.findOne({ username:req.params.username }, function (err, company) {
            if (!err) {
                if(company) {
                    models.Ingredient.find({ company:company.username }, function (err, ingredients) {
                        if (!err) {
                            res.send(ingredients);
                            next();
                        }
                        else {
                            console.error("Failed to read ingredients from database:", err);
                            next(new restify.InternalError("Failed to read ingredients due to an unexpected internal error"));
                        }
                    });
                } else {
                    next(new restify.ResourceNotFoundError("No company found with the given id"));
                }
            }
            else {
                console.error("Failed to read ingredients from database:", err);
                next(new restify.InternalError("Failed to read ingredients due to an unexpected internal error"));
            }
        });
    }); //FIXME cannot work like this, because it will conflict with the above method

    //TODO: search ingredients based on name
}