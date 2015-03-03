/**
 * Created by Pavel Prochazka on 22/10/14.
 * Module for all company specific API calls.
 */

var restify = require('restify');
var crypto = require('crypto');

module.exports = function (server, nconf) {
    var uploadSizeLimit = 1048576; // bytes     (1048576 bytes = 1 mb)
    var expiryTime = 900000; // ms              (900000ms = 15 minutes)
    var groupRegex = /^gastronomist|company|ingredient|recipe$/; // Allowed folders
    var bucket = "nourriture"; // S3 bucket to use

    // Retrieve a policy token that allows image upload for a specific company
    server.get('/upload/token/:group/:entity', function (req, res, next) {
        console.log('Upload token requests for "' + req.params.entity + '" (' + req.params.entity + ')');

        var group = req.params.group;
        var entity = req.params.entity;

        // TODO: Add authorization and ensure users only can upload with filename of their own username

        // Verify group parameter (Very important because it dictates which folder to put the file in)
        if(group) {
            var match = group.match(groupRegex);
            if(match) {
                group = match[0];
            } else {
                throw new restify.InvalidContentError("Invalid group");
            }
        } else {
            throw new restify.InvalidContentError("A group needs to be defined");
        }

        // Policy content
        var s3Policy = {
            "expiration": new Date(Date.now() + expiryTime),
            "conditions": [
                ["starts-with", "$key", group + "/" + entity],
                {"bucket": bucket},
                {"acl": "public-read"},
                ["starts-with", "$Content-Type", "image/"],
                ["content-length-range", 0, uploadSizeLimit]
            ]
        };

        // stringify and encode the policy
        var stringPolicy = JSON.stringify(s3Policy);
        var base64Policy = Buffer(stringPolicy, "utf-8").toString("base64");

        // sign the base64 encoded policy
        var signature = crypto.createHmac("sha1", nconf.get("amazon").secretAccessKey)
            .update(new Buffer(base64Policy, "utf-8")).digest("base64");

        res.send({
            s3Policy: base64Policy,
            s3Signature: signature
        });
        next();
    });
};