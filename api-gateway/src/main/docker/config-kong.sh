#Create the services.
curl -S -s -i -X POST --url http://api-gateway:8001/services --data "name=group-service" --data-urlencode "url=http://group-service:8080/groups"
curl -S -s -i -X POST --url http://api-gateway:8001/services --data "name=user-service" --data-urlencode "url=http://user-service:8080/users"
curl -S -s -i -X POST --url http://api-gateway:8001/services --data "name=movie-service-movies-mock" --data-urlencode "url=http://movie-service:8080/Mock_movies"
curl -S -s -i -X POST --url http://api-gateway:8001/services --data "name=movie-service-movies" --data-urlencode "url=http://movie-service:8080/movies"
curl -S -s -i -X POST --url http://api-gateway:8001/services --data "name=movie-service-search" --data-urlencode "url=http://movie-service:8080/search"
curl -S -s -i -X POST --url http://api-gateway:8001/services --data "name=movie-service-search-mock" --data-urlencode "url=http://movie-service:8080/Mock_search"
curl -S -s -i -X POST --url http://api-gateway:8001/services --data "name=movie-service-discover" --data-urlencode "url=http://movie-service:8080/discover"

#Create the routes
curl -S -s -i -X POST  --url http://api-gateway:8001/services/group-service/routes --data-urlencode "paths[]=/api/v1/groups"
curl -S -s -i -X POST  --url http://api-gateway:8001/services/user-service/routes --data-urlencode "paths[]=/api/v1/users"
curl -S -s -i -X POST  --url http://api-gateway:8001/services/movie-service-movies-mock/routes --data-urlencode "paths[]=/api/v1/movie-service/Mock_movies"
curl -S -s -i -X POST  --url http://api-gateway:8001/services/movie-service-movies/routes --data-urlencode "paths[]=/api/v1/movie-service/movies"
curl -S -s -i -X POST  --url http://api-gateway:8001/services/movie-service-search/routes --data-urlencode "paths[]=/api/v1/movie-service/search"
curl -S -s -i -X POST  --url http://api-gateway:8001/services/movie-service-search-mock/routes --data-urlencode "paths[]=/api/v1/movie-service/Mock_search"
curl -S -s -i -X POST  --url http://api-gateway:8001/services/movie-service-discover/routes --data-urlencode "paths[]=/api/v1/movie-service/discover"


