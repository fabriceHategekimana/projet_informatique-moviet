#!/bin/sh

echo "Killing Docker containers and removing them"

# Kill and remove Docker containers

docker kill group-service 2> /dev/null

# Add more containers below


# Remove docker containers
docker rm group-service 2> /dev/null

# Add more containers below
