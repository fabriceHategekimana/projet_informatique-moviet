#!/bin/sh

# Ubuntu 20.04

# Remember to do chmod u+x on this file.

echo "Building locally the project and images and running the Docker containers"

# Build project locally, build Docker images and run Docker containers

sudo dockerd &

# Kill and remove docker container first
sudo scripts/kill_remove_containers.sh


# mvn clean install
sudo mvn clean install -Ppackage-docker-image
# integration tests won't work if we do not run docker containers before..

# currently only has group-service
sudo docker run -p 10080:8080 --name=group-service unige/group-service &
