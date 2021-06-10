if [ "unige" == $(echo $1 | grep -o "unige") ]; then
	nom=$(echo $1 | sed "s/unige\///")
	sudo docker tag $1 fabricehategekimana/$nom:$2
	sudo docker push fabricehategekimana/$nom:$2
else
	sudo docker tag $1 fabricehategekimana/$1:$2
	sudo docker push fabricehategekimana/$1:$2
fi
