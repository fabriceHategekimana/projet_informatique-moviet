#!/bin/sh

# Ubuntu 20.04

# Remember to do chmod u+x on this file.

echo "Building locally the project and images and running the Docker containers"

# Build project locally, build Docker images and run Docker containers

sudo dockerd &

# mvn clean install
sudo mvn clean install -Ppackage-docker-image

# Clean docker container first
sudo docker rm group-service 2> /dev/null # stderror -> dev/null

# currently only has group-service
sudo docker run -p 10080:8080 --name=group-service unige/group-service &
