#!/bin/sh

echo "Killing Docker containers and removing them"

# Kill and remove Docker containers
# all microservices
sudo docker-compose -f docker-compose/docker-compose-microservices.yml down 2> /dev/null
# api-gw
sudo docker-compose -f docker-compose/docker-compose-api-gw.yml down 2> /dev/null