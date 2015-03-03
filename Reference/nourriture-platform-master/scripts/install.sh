#!/bin/sh
# Procedures to run after deployment in production environment
# or in a fresh development environment.
#
# Niels Søholm (2014-10-11)

# Extract archieve
unzip -o *.zip
cd nourriture-*

# Install node.js dependency modules using NPM
npm install