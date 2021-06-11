#Creates the services.
curl -S -s -i -X POST --url http://api-gateway:8001/services --data "name=group-service" --data-urlencode "url=http://group-service:8080/groups"
curl -S -s -i -X POST --url http://api-gateway:8001/services --data "name=user-service" --data-urlencode "url=http://user-service:8080/users"
curl -S -s -i -X POST --url http://api-gateway:8001/services --data "name=movie-service-movies-mock" --data-urlencode "url=http://movie-service:8080/Mock_movies"
curl -S -s -i -X POST --url http://api-gateway:8001/services --data "name=movie-service-movies" --data-urlencode "url=http://movie-service:8080/movies"
curl -S -s -i -X POST --url http://api-gateway:8001/services --data "name=movie-service-search" --data-urlencode "url=http://movie-service:8080/search"
curl -S -s -i -X POST --url http://api-gateway:8001/services --data "name=movie-service-discover-mock" --data-urlencode "url=http://movie-service:8080/Mock_discover"
curl -S -s -i -X POST --url http://api-gateway:8001/services --data "name=movie-service-discover" --data-urlencode "url=http://movie-service:8080/discover"

curl -S -s -i -X POST  --url http://api-gateway:8001/services/group-service/routes --data-urlencode "paths[]=/api/v1/groups"
curl -S -s -i -X POST  --url http://api-gateway:8001/services/user-service/routes --data-urlencode "paths[]=/api/v1/users"
curl -S -s -i -X POST  --url http://api-gateway:8001/services/movie-service-movies-mock/routes --data-urlencode "paths[]=/api/v1/movie-service/Mock_movies"
curl -S -s -i -X POST  --url http://api-gateway:8001/services/movie-service-movies/routes --data-urlencode "paths[]=/api/v1/movie-service/movies"
curl -S -s -i -X POST  --url http://api-gateway:8001/services/movie-service-search/routes --data-urlencode "paths[]=/api/v1/movie-service/search"
curl -S -s -i -X POST  --url http://api-gateway:8001/services/movie-service-discover-mock/routes --data-urlencode "paths[]=/api/v1/movie-service/Mock_discover"
curl -S -s -i -X POST  --url http://api-gateway:8001/services/movie-service-discover/routes --data-urlencode "paths[]=/api/v1/movie-service/discover"

movie-service/
#Enable the Open ID Plugin
#curl -S -s -i -X POST  --url http://api-gateway:8001/plugins --data "name=jwt" --data "enabled=true"
#curl -S -s -i -X POST  --url http://api-gateway:8001/consumers  --data "username=api-sso-proxied"   --data "custom_id=api-sso-proxied"
#curl -S -s -i -X POST  --url http://api-gateway:8001/consumers/api-sso-proxied/jwt   -F "algorithm=RS256"  -F "rsa_public_key=@/tmp/keycloak_rsa_provider-key-pub.pem" -F "key=https://localhost/auth/realms/apigw"
#curl -S -s -i -X POST  --url http://api-gateway:8001/consumers  --data "username=api-sso-not-proxied"   --data "custom_id=api-sso-not-proxied"
#curl -S -s -i -X POST  --url http://api-gateway:8001/consumers/api-sso-not-proxied/jwt   -F "algorithm=RS256"  -F "rsa_public_key=@/tmp/keycloak_rsa_provider-key-pub.pem" -F "key=http://iam:8080/auth/realms/apigw"
