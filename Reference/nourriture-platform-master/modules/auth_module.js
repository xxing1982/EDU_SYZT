/**
 * Created by niels on 12/1/14.
 *
 * Module for handling authentication and authorization
 */

var passport = require("passport");
var LocalStrategy = require('passport-local').Strategy;
var bcrypt = require("bcrypt");
var restify = require("restify");
var auth = require("authorized");

var SALT_WORK_FACTOR = 10;  // Intensity of password hashing (a factor of 10 should be fairly difficult to decrypt)

// Roles (All users have 1 (and only 1) role)
var getRoleFunc = function(role) { return function (req, done) { return done(null, req.user && req.user.role == role) } };
auth.role("raw", getRoleFunc("raw"));
auth.role("gastro", getRoleFunc("gastro"));
auth.role("comp", getRoleFunc("comp"));
auth.role("both", getRoleFunc("both"));
auth.role("admin", getRoleFunc("admin"));

// Actions (Platform requests may trigger one of these actions)
auth.action("add recipe", ["gastro", "both", "admin"]);
auth.action("edit recipe", ["gastro", "both", "admin"]);
auth.action("add ingredient", ["comp", "both", "admin"]);
auth.action("edit ingredient", ["comp", "both", "admin"]);
auth.action("view profile", ["raw", "gastro", "comp", "both", "admin"]); // In the case of gastronomist "profile = Gastronomist + User" and for company "profile = Company + User". For others just User
auth.action("edit profile", ["raw", "gastro", "comp", "both", "admin"]);
auth.action("administer profiles", ["admin"]);


module.exports = function (server, models) {

    // BCrypt middleware for User model (Ensures password is always hashed before being saved)
    models.User.schema.pre('save', function(next) {
        var user = this;
        // Skip if password not modified
        if(!user.isModified('password')) return next();
        // Hash password using BCrypt
        bcrypt.genSalt(SALT_WORK_FACTOR, function(err, salt) {
            if(err) return next(err);

            bcrypt.hash(user.password, salt, function(err, hash) {
                if(err) return next(err);
                user.password = hash;
                next();
            });
        });
    }); // TODO: Make similar middleware to ensure password is never retrieved (post read?)

    // Use bcrypt to compare passwords
    var comparePassword = function(user, candidatePassword, callback) {
        bcrypt.compare(candidatePassword, user.password, function(err, isMatch) {
            if(err) return callback(err);
            callback(null, isMatch);
        });
    };

    // Session
    passport.serializeUser(function(user, done) {
        done(null, user.username);

    });
    passport.deserializeUser(function(username, done) {
        models.User.findOne({ username: username }, function(err, user) {
           if(err) {
               return done(err);
           } else {
                done(null, user);
           }
        });
    });

    // Local authentication strategy
    passport.use(new LocalStrategy(function(username, password, done) {
        // TODO: Support e-mail login as well (Simply don't allow @-sign in usernames and choose lookup technique based on its presence)
        models.User.findOne({ username: username }, function(err, user) {
            // (Unexpected) Failed to lookup user
            if (err) { return done(err); }

            // User not found
            if (!user) {
                return done(null, false, { message: 'Unknown user ' + username });
            }

            // Validate password
            comparePassword(user, password, function(err, isMatch) {
                // (Unexpected) Failed to validate password
                if (err) return done(err);
                if(isMatch) {
                    // Success!
                    return done(null, user);
                } else {
                    // Invalid password
                    return done(null, false, { message: 'Invalid password' });
                }
            });
        });
    }));

    server.use(passport.initialize());
    server.use(passport.session());

    server.post('/login', function(req, res, next) {
        passport.authenticate('local', function(err, user, info) {
            if (err) {
                return next(new restify.InternalError("Failed to login due to an unexpected error"));
            }
            if (!user) {
                return next(new restify.InvalidCredentialsError("Invalid or missing credentials in request"))
            }
            req.logIn(user, function(err) {
                if (err) {
                    return next(err);
                }
                return res.send(user);
            });
        })(req, res, next);
    });

    server.get('/logout', function(req, res, next) {
        req.logout();
        res.send({ message: "Logged out successfully" });
        next();
    });

    server.post('/user', function (req, res, next) {
        var user = req.body;
        user.authMethod = "local";
        user.role = "raw";

        var newUser = new models.User(user);
        newUser.save(function (err, resUser) {
            if(!err) {
                res.send(resUser); // TODO: Don't return password, not even if it's hashed
                next();
            } else {
                if(err.name == "ValidationError") {
                    next(new restify.InvalidContentError(err.toString()));
                } else {
                    // TODO: Return more appropriate error when username already exists
                    console.error("Failed to insert user into database:", err);
                    next(new restify.InternalError("Failed to insert user due to an unexpected internal error"));
                }
            }
        });
    });

    server.get('/user',
        auth.can("administer profiles"),
        function (req, res, next) {
            models.User.find({}, { password:0 }, function (error, users) {
                if(!error) {
                    res.send(users);
                    next();
                } else {
                    console.error("Failed to read users from database:", error);
                    next(new restify.InternalError("Failed to read users due to an unexpected internal error"));
                }
            });
        }
    );

    server.put('/user/:username',
        auth.can("edit profile"),
        function (req, res, next) {
            // Authorization (Only edit own profile, unless administrator)
            if(req.user.username != req.params.username && req.user.role != "admin") {
                throw new auth.UnauthorizedError("edit profile");
            }

            // Retrieve existing user, overwrite fields, validate and save
            models.User.findOne({ username:req.params.username }, { password:0 }, function(err, user) {
                if(!err) {
                    if(user) {
                        // Overwrite fields with value from request body
                        for (var key in req.body) {
                            user[key] = req.body[key];
                        }

                        // Validate and save
                        user.save(function (err) {
                            if(!err) {
                                res.send(user);
                                next();
                            } else {
                                if(err.name == "ValidationError") {
                                    next(new restify.InvalidContentError(err.toString()));
                                } else {
                                    console.error("Failed to update user in database:", err);
                                    next(new restify.InternalError("Failed to update user due to an unexpected internal error"));
                                }
                            }
                        });
                    } else {
                        // No user found with given username
                        next(new restify.ResourceNotFoundError("No users found with the given username"));
                    }
                } else {
                    // Database connection error
                    console.error("Failed to query database for user profile:", err);
                    next(new restify.InternalError("Failed to update user due to an unexpected internal error"));
                }
            });
        }
    );

    server.del('/user/:username',
        auth.can("administer profiles"),
        function(req, res, next) {
            models.User.findOneAndRemove({ username:req.params.username }, { password:0 }, function(err, deletedUser) {
                if(!err) {
                    if(deletedUser) {
                        res.send(deletedUser);
                        next();
                    } else {
                        next(new restify.ResourceNotFoundError("No user found with the given username"));
                    }
                } else {
                    console.error("Failed to delete user profile from database:", err);
                    next(new restify.InternalError("Failed to delete user due to an unexpected internal error"));
                }
            });
        }
    );

    // Tiny endpoint for verifying whether we are logged in // TODO: Remove when authorization has been implemented across application
    server.get('/isloggedin',
        auth.can("view profile"),
        function (req, res, next) {
            res.send(req.user);
        }
    );
};
