#!/bin/bash

# Clean up any old build
rm -f -r dist

# Retrieve dependencies
npm install

# Build and compress
grunt build --buildnumber=0.1.$BUILD_NUMBER