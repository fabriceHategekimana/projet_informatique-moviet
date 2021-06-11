#!/bin/sh

# Ubuntu 20.04

# Remember to do chmod u+x on this file.

echo "Building locally the project and images and running the Docker containers"

# Build project locally, build Docker images and run Docker containers
# Launch docker daemon
sudo dockerd &

# Kill and remove docker container first then build using mvn clean install -Ppackage-docker-image
sudo scripts/build_locally.sh

# integration tests won't work if we do not run docker containers before..

# all microservices
sudo docker-compose -f docker-compose/docker-compose-microservices.yml up -d

# api-gateway and postgres:10
sudo docker-compose -f docker-compose/docker-compose-api-gw.yml up -d

