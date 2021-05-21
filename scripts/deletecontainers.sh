containers=$(echo unige/user-service unige/api-gateway unige/movie-service unige/group-service)

# trucs en plus
prefix=docker.io/
for container in containers;
do
	name=${container:6}
	microk8s ctr images rm $prefix$container
done;

# for web ui
microk8s ctr images rm web-ui
