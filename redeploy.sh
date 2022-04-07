#!/bin/bash

# chmod u+x redeploy.sh to make file executable

echo "stopping release-demon container"
docker stop release-demon
echo "release-demon container was stopped"

echo "removing release-demon container"
docker rm release-demon
echo "release-demon container was removed"

echo "removing release-demon image"
docker image rm release-demon
echo "release-demon image was removed"

echo "building new release-demon image"
docker build -t release-demon .
echo "release-demon image was build"

echo "running release-demon and mysql from docker-compose"
docker-compose up -d