/**
 * Created by niels on 11/9/14.
 */

module.exports = function (nconf) {
    // 1st priority: Command-line arguments
    nconf.argv();

    // 2nd priority: Custom config file
    nconf.file({ file: "config.json"});

    // 3rd priority: Defaults
    nconf.defaults({
        "name": "Nourriture Server!",
        "version": "0.0.1",
        "port": 2121,
        "connection-string": "mongodb://localhost:27017/nourriture-app",
        "allowCORS": true,
        "allowOrigins": ["http://localhost:8080"],
        "sessionSecret": "allyourbasearebelongtous"
    });
};