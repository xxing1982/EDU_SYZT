#!/bin/sh
# Procedures that will deploy and install our application
# using SSH.
#
# NOTE: Expects variables
#   - $ENVIRONMENT: Used root folder and process uid
#
# Niels Søholm (2014-12-02)

scp nourriture-webapp-0.1.*.zip training:/srv/$ENVIRONMENT/nourriture-webapp.zip

ssh training <<EOF
  cd /srv/$ENVIRONMENT/

  rm -f -r nourriture-webapp

  unzip -o *.zip

  cp config.js nourriture-webapp/config.js

  exit
EOF