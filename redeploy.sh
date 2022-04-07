#!/bin/bash

# chmod u+x redeploy.sh to make file executable

docker stop release-demon

docker rm release-demon

docker image rm release-demon

docker build -t release-demon .

docker-compose up -d