#get rid of all images, networks, and volumes

echo "removing the images and networks, make sure you have killed and removed all your containers"
echo "--------------------------------------------------------------------------------"
sudo docker system prune -a
echo "--------------------------------------------------------------------------------"
echo "removing the volumes"
echo "--------------------------------------------------------------------------------"
sudo docker volume prune
echo "--------------------------------------------------------------------------------"
echo "images, volumes and networks succesfully removes"

echo "--------------------------------------------------------------------------------"
echo "images:"
sudo docker images
echo "--------------------------------------------------------------------------------"
echo "volumes:"
sudo docker volume ls
echo "--------------------------------------------------------------------------------"
echo "networks:"
sudo docker network ls

