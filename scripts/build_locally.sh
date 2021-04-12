
# Kill and remove docker container first
sudo scripts/kill_remove_containers.sh

# Build, obtain docker images for microservices and api-gateway
sudo mvn clean install -Ppackage-docker-image