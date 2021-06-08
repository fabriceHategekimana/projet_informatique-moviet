#If contain web-ui
if [ "unige" == $(echo $1 | grep -o "unige") ]; then
	nom=$(echo $1 | sed "s/unige\///")
	sudo docker tag $1 fabricehategekimana/$nom
	sudo docker push fabricehategekimana/$nom
else
	sudo docker tag $1 fabricehategekimana/$1
	sudo docker push fabricehategekimana/$1
fi
