/**
 * Created by niels on 11/11/14.
 */

var express = require("express");
var async = require("async");

var app = express();
app.use(express.static(__dirname + "/static")); // Static content
app.use(express.static(__dirname + "/node_modules/knockout/build/output"));
app.use(express.static(__dirname + "/node_modules/jquery/dist"));

var pichost = "http://9la.dk/img/";
var outdir = "../pod-export/output";
var podExport = require("../pod-export/pod-export")(pichost, outdir);


// Search request
app.get("/search/:field/:name", function (req, res) {
    console.log("Search request received!");

    // Retrieve parameters
    var keyword = req.params.name;
    var field = req.params.field;
    var quick = false;
    if (req.query.quick) {
        quick = JSON.parse(req.query.quick);
    }

    async.waterfall([
        // Make query
        function (callback) {
            switch (field) {
            case "category":
                podExport.queryCategory(keyword, quick, callback);
                break;
            case "ename":
                podExport.queryExactName(keyword, quick, callback);
                break;
            case "name":
                podExport.queryName(keyword, quick, callback);
                break;
            }
        },
        // Respond
        function (result, callback) {
            res.set("Content-Type", "json/application");
            res.send(result);
            callback(null, result);
        }
    ],
        // Error handling / final callback
        function (err/*, result*/) {
            if (!err) {
                res.end();
            } else {
                res.status(500);
                res.send({ message: "Failed to retrieve due to an internal error" });
            }
        }
    );
});


var server = app.listen(8080, function () {
    console.log("Listening on port %d", server.address().port);
});