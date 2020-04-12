#!/bin/sh

export DATABASE_DB=a1database:5432/phdb
export KTOR_ENV=prod
export JAVA_OPTS="-Xmx300m -Xss512k -XX:CICompilerCount=2 -Dfile.encoding=UTF-8"

cd ~/appoint.one
git pull

echo "Generisem Backend"

cd ~/appoint.one/backend
./gradlew build -x test

echo "Generisem Frontend"

cd ~/appoint.one/frontend
yarn install
yarn build

echo "Kopiram Backend"

sudo cp -r ~/appoint.one/backend/build/libs/* /opt/appoint
sudo chown -R jvmapps:appmgr /opt/appoint

echo "Kopiram Frontend"

sudo cp -r ~/appoint.one/frontend/dist/. /var/www/html

sudo systemctl restart appoint.service

echo "Zavrseno!"