#!/bin/sh -x

echo 'Starting nginx on ' $PORT
nginx

echo 'Starting server...'
#/appoint1-shadow/bin/appoint1
cd server && ./gradlew run -x test
