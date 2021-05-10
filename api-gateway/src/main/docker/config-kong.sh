#Creates the services.
curl -S -s -i -X POST --url http://api-gateway:8001/services --data "name=group-service" --data-urlencode "url=http://group-service:8080/groups"
curl -S -s -i -X POST --url http://api-gateway:8001/services --data "name=tmdb-requests-mock" --data-urlencode "url=http://tmdb-requests-service:8080/Mock_movies"
curl -S -s -i -X POST --url http://api-gateway:8001/services --data "name=tmdb-requests-service" --data-urlencode "url=http://tmdb-requests-service:8080/movies"

#create the routes
curl -S -s -i -X POST  --url http://api-gateway:8001/services/group-service/routes --data-urlencode "paths[]=/api/v1/groups" 
curl -S -s -i -X POST  --url http://api-gateway:8001/services/tmdb-requests-mock/routes --data-urlencode "paths[]=/api/v1/Mock_movies"
curl -S -s -i -X POST  --url http://api-gateway:8001/services/tmdb-requests-service/routes --data-urlencode "paths[]=/api/v1/movies"

#enable the OIDC plugin
curl -S -s -i -X POST  --url http://api-gateway:8001/plugins --data "name=oidc" --data "enabled=true"

#configure the oidc plugin to use google as idP
curl -X POST -d 'name=oidc' -d 'config.client_id=858064340266-mti3r0hn9v1dihf9e5vvrs6aini14me6.apps.googleusercontent.com' -d 'config.client_secret =n7es6KYQ5Yrf-j2whND6bhvW' -d 'config.discovery=https://accounts.google.com/.well-known/openid-configuration' http://api-gateway:8001/plugins
