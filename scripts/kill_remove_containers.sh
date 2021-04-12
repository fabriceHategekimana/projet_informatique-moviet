#!/bin/sh

echo "Killing Docker containers and removing them"

# Kill and remove Docker containers
sudo docker kill group-service api-gateway api-gateway-init postgres:10 2> /dev/null

# Add more containers below


# Remove docker containers
sudo docker rm group-service api-gateway api-gateway-init postgres:10 2> /dev/null

# Add more containers below
