#!/bin/bash

# Drop any existing samples
mongo <<EOF
    use nourriture-android-test
    db.consumers.drop();
    db.moments.drop();
EOF

# Do import
mongoimport --db nourriture-android-test --collection consumers test/samples/consumers.json
mongoimport --db nourriture-android-test --collection moments test/samples/moments.json