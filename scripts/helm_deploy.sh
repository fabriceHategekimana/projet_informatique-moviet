# clean helm directory
rm helm-charts/*

cp docker-compose/docker-compose-microservices.yml helm-charts

#change version
cd helm-charts
sed -i '1d' docker-compose-microservices.yml
sed -i '1iversion: "3"' docker-compose-microservices.yml

#change restart to "always"
sed -i '20s/on-failure/always/g' docker-compose-microservices.yml

# create a kubernetes ymls
kompose convert --file docker-compose-microservices.yml

# delete the docker-compose copy
rm docker-compose-microservices.yml

# create a new helm chart
helm create moviet

# delete all unwanted files in the helm chart
rm moviet/values.yaml
cd moviet/templates

# import all the necessary .yaml files
rm *.yaml NOTES.txt tests
mv ../../*.yaml .
cd ../../
touch moviet/values.yaml

# checking if the template is correct
helm template moviet moviet

# run the cluster
helm install moviet moviet

# see the installation of the cluster
kube get all

cd ..

#containers=$(echo unige/user-service unige/api-gateway unige/movie-service unige/group-service)
#
## trucs en plus
##prefix=docker.io/
#for container in containers;
#do
	#name=${container:6}
	#sudo docker save $container > $name.tar
	#microk8s ctr images import $name.tar
#done;
