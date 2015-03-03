#!/bin/sh
# Procedures that will deploy and install our application
# using SSH.
#
# NOTE: Expects variables
#   - $ENVIRONMENT: Used root folder and process uid
#
# Niels Søholm (2014-11-08)

scp nourriture-0.1.*.zip training:/srv/$ENVIRONMENT/nourriture.zip

ssh training <<EOF
  cd /srv/$ENVIRONMENT/

  forever stop $ENVIRONMENT
  rm -f -r nourriture-*
  
  mkdir -p logs

  unzip -o *.zip
  cd nourriture-*

  npm install
  cp ../config.json config.json
  forever -a --uid "$ENVIRONMENT" -l "/srv/$ENVIRONMENT/logs/forever-dev.log" -o "/srv/$ENVIRONMENT/logs/forever-dev-out.log" -e "/srv/$ENVIRONMENT/logs/forever-dev-err.log" -p "/srv/$ENVIRONMENT/.forever" --minUptime 1000 --spinSleepTime 1000 start server.js

  exit
EOF