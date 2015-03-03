#!/bin/sh
# Procedures to run our test framework and output results
#
# Pavel Prochazka (2014-12-13)

# First of all, need to install dependencies
npm install

# NO NEED to start MongoDB, because it's running on Jenkins server as a service!

# Set an ENVIRONMENT VARIABLE, which is accessible for all started processes
export dbcon="mongodb://localhost:27017/nourriture-app-test"

# Replace the "connection-string" inside NodeJS config with the ENVIRONMENT VARIABLE
# Adding "&" to place a job into the background (like opening a new tab), so that the "mocha" doesn't wait
node server.js --connection-string $dbcon &

mocha

# For local debug purposes (kill running NodeJS server) - Jenkins will take care of this
# killall node