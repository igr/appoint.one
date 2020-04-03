#!/bin/sh -x

#/appoint1-shadow/bin/appoint1 &
cd server && ./gradlew run -x test &
nginx -g 'daemon off;'