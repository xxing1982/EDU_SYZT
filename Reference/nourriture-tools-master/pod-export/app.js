/**
 * Command-line utility for invoking pod-export
 *
 * Usage:
 *      node app.js --out=path/to/outdir [--category=apple|--name=onions|--ename=onions] --pichost=www.mypodpicturehost.com
 *
 * Created by niels on 11/10/14.
 */

// Parse command-line arguments
var parseArgs = require('minimist');
var args = parseArgs(process.argv);

// Initialize export module
var podExport = require('./pod-export.js')(args["pichost"], args["out"]);

// How to query? (exact name, name containing or category)
if(args["ename"]) {
    console.log("Starting export for products with exact name: \"" + args["ename"] + "\" ...");
    podExport.queryExactName(args["ename"], args["q"]);
}
if(!args["ename"] && args["name"]) {
    console.log("Starting export for products with name containing: \"" + args["name"] + "\" ...");
    podExport.queryName(args["name"], args["q"]);
}
if(!args["ename"] && !args["name"] && args["category"]) {
    console.log("Starting export for products with category: \"" + args["category"] + "\" ...");
    podExport.queryCategory(args["category"], args["q"]);
}