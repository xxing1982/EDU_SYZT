/**
 * Created by Pavel Prochazka on 23/10/14.
 * The android backend for Nourriture (based on Restify)
 */

var restify         = require('restify');
var mongoose        = require("mongoose");
var models          = require("./models/data_model")(mongoose);
var aws             = require("aws-sdk");
var nconf           = require("nconf");

// Load configuration
require("./modules/config_module")(nconf);

// Initialize server
var server = restify.createServer({ name: nconf.get("name"), version: nconf.get("version") });
server.use(restify.fullResponse());
server.use(restify.bodyParser({ mapParams : false }));
server.use(restify.queryParser());

aws.config.update(nconf.get("amazon"));

// Server startup function, should be run when all routes have been registered and we are ready to listen
var startServer = function() {
    var db = mongoose.connection;

    // On failure to connect, abort server startup and show error
    db.on('error', console.error.bind(console, 'connection error:'));

    // On successful connection, finalize server startup
    db.once('open', function() {
        console.log("Connected to database successfully!");

        server.listen(nconf.get("port"), function () {
            console.log('- - - %s listening at %s - - -', server.name, server.url);
            require('./utilities/document')(server.router.mounts, 'restify');
        });
    });

    mongoose.connect(nconf.get("connection-string"));
};

//CONSUMER related API calls
var consumerModule  = require('./modules/consumer_module')(server, models, aws);

//MOMENT related API calls
var momentModule = require('./modules/moment_module')(server, models);

// Connect to DB and start listening
startServer();