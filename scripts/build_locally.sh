
# Kill and remove docker container first
sudo scripts/kill_remove_containers.sh

# Remove tmp/target/*, the temporary DB used for Integration Tests with CodeHaus Mojo
# sudo rm /tmp/target/*

# Build, obtain docker images for microservices and api-gateway
sudo mvn clean install -Ppackage-docker-image
