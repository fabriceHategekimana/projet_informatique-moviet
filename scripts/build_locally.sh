
# Kill and remove docker container first
sudo scripts/kill_remove_containers.sh

# Build, obtain docker images for microservices and api-gateway, IT are not runned here
# sudo mvn clean install -Ppackage-docker-image
sudo mvn clean install -Ppackage-docker-image-with-IT