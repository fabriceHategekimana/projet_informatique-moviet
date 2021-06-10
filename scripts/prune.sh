#!/bin/bash
#get rid of all images, networks, and volumes

red=`tput setaf 1`
green=`tput setaf 2`
reset=`tput sgr0`
echo "${red}--------------------------------------------------------------------------------${reset}"
echo "${red}removing the images and networks, make sure you have killed and removed all your containers${reset}"
echo "${red}--------------------------------------------------------------------------------${reset}"
sudo docker system prune -a
echo "${red}--------------------------------------------------------------------------------${reset}"
echo "${red}removing the volumes${reset}"
echo "${red}--------------------------------------------------------------------------------${reset}"
sudo docker volume prune
echo "${red}--------------------------------------------------------------------------------${reset}"
echo "${red}images, volumes and networks succesfully removed${reset}"

echo "${red}--------------------------------------------------------------------------------${reset}"
echo "${red}images:${reset}"
sudo docker images
echo "${red}--------------------------------------------------------------------------------${reset}"
echo "${red}volumes:${reset}"
sudo docker volume ls
echo "${red}--------------------------------------------------------------------------------${reset}"
echo "${red}networks:${reset}"
echo "${red}--------------------------------------------------------------------------------${reset}"
sudo docker network ls

