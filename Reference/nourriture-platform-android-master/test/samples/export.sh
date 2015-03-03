#!/bin/bash
mongoexport --db nourriture-android-test --collection consumers --out test/samples/consumers.json
mongoexport --db nourriture-android-test --collection moments --out test/samples/moments.json