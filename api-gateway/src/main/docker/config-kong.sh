#Creates the services.
#curl -S -s -i -X POST --url http://api-gateway:8001/services --data "name=-service" --data-urlencode "url=http://counterparty-service:8080/counterparties"

#Creates the routes
#curl -S -s -i -X POST  --url http://api-gateway:8001/services/counterparty-service/routes --data-urlencode "paths[]=/api/v1/counterparty" 


