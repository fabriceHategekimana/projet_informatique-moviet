#Creates the services.


curl -S -s -i -X POST --url http://api-gateway:8001/services --data "name=group-service" --data-urlencode "url=http://group-service:8080/groups"
curl -S -s -i -X POST --url http://api-gateway:8001/services --data "name=movie-service-mock" --data-urlencode "url=http://movie-service:8080/Mock_movies"
curl -S -s -i -X POST --url http://api-gateway:8001/services --data "name=movie-service-service" --data-urlencode "url=http://movie-service:8080/movies"

curl -S -s -i -X POST  --url http://api-gateway:8001/services/group-service/routes --data-urlencode "paths[]=/api/v1/groups" 
curl -S -s -i -X POST  --url http://api-gateway:8001/services/movie-service-mock/routes --data-urlencode "paths[]=/api/v1/Mock_movies"
curl -S -s -i -X POST  --url http://api-gateway:8001/services/movie-service-service/routes --data-urlencode "paths[]=/api/v1/movies"


#Enable the Open ID Plugin
#curl -S -s -i -X POST  --url http://api-gateway:8001/plugins --data "name=jwt" --data "enabled=true"
#curl -S -s -i -X POST  --url http://api-gateway:8001/consumers  --data "username=api-sso-proxied"   --data "custom_id=api-sso-proxied"
#curl -S -s -i -X POST  --url http://api-gateway:8001/consumers/api-sso-proxied/jwt   -F "algorithm=RS256"  -F "rsa_public_key=@/tmp/keycloak_rsa_provider-key-pub.pem" -F "key=https://localhost/auth/realms/apigw"
#curl -S -s -i -X POST  --url http://api-gateway:8001/consumers  --data "username=api-sso-not-proxied"   --data "custom_id=api-sso-not-proxied"
#curl -S -s -i -X POST  --url http://api-gateway:8001/consumers/api-sso-not-proxied/jwt   -F "algorithm=RS256"  -F "rsa_public_key=@/tmp/keycloak_rsa_provider-key-pub.pem" -F "key=http://iam:8080/auth/realms/apigw"
