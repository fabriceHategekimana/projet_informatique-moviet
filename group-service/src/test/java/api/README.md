# REST API IT

We use restassured to test the REST API. The integrations tests are done after the maven package phase and we do not use any docker containers for the IT.
We had to use H2 database but for IT (when we'll run containers by using docker-compose after the maven lifecycle, the environment variables defined in docker-compose are like overriding the datasource `groupds`, connection url etc. etc. to link with the group-service-database instead of the H2 database)
