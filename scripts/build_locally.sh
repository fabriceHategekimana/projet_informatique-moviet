
# Kill and remove docker container first
sudo scripts/kill_remove_containers.sh

# Build, obtain docker images for microservices and api-gateway, docker containers are runned before IT too !
sudo mvn clean install -Ppackage-docker-image