
containers=$(echo unige/user-service unige/api-gateway unige/movie-service unige/group-service)

# trucs en plus
#prefix=docker.io/
for container in $containers;
do
	name=${container:6}
	sudo docker save $container > $name.tar
	microk8s ctr images import $name.tar
	rm $name.tar
done;

# for web ui

sudo docker save web-ui > web-ui.tar
microk8s ctr images import web-ui.tar
