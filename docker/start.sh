#!/bin/sh -x

echo 'Starting nginx on' $PORT
envsubst < /etc/nginx/conf.d/default-conf > /etc/nginx/conf.d/default.conf
nginx

echo 'Starting server...'
#/appoint1-shadow/bin/appoint1
cd server && ./gradlew run -x test
