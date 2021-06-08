#DÉFINITION DES SECRETS
helm="microk8s helm3"
kube="microk8s kubectl"

# secret à garder
clee1=c1
clee2=c2
clee3=c3

secret1=s1
secret2=s2
secret3=s3

# clées pour le unseal
key1=coDou/up3yA+HM9rwjdkG+ntlFjb34caJmZ8pHgWiYKh
key2=pmhzhTgLHFFkx2n82OCeSHwCa87srdX9ET/F5FAw3Yb7
key3=o7CGWviMptho0Af9Bt8+dfG7lMYNk/YffYeve9G3wIGy

# token de vault
token=s.GLlu5AKHYLnwF2nJdotwQp88

if [ "$1" == "init" ]; then
	#Installer le chart vault
	helm repo add hashicorp https://helm.releases.hashicorp.com
	#lancer vault 
	helm install vault helm-charts/vault
	sleep 5
	kube exec -it vault-0 -- vault operator init

elif [ "$1" == "unseal" ]; then
	## desceller vault (pour chaque unseal key [unseal key])
	kube exec -it vault-0 -- vault operator unseal $key1
	kube exec -it vault-0 -- vault operator unseal $key2
	kube exec -it vault-0 -- vault operator unseal $key3

elif [ "$1" == "set" ]; then

	ip=$(kube describe service/vault | grep IP: | sed 's/IP:\s\+//')

	#1
	curl -X POST -H "X-Vault-Request: true" -H "X-Vault-Token: $token" -d '{"type":"kv-v2","description":"","config":{"options":null,"default_lease_ttl":"0s","max_lease_ttl":"0s","force_no_cache":false},"local":false,"seal_wrap":false,"external_entropy_access":false,"options":null}' http://$ip:8200/v1/sys/mounts/internal

	#2
	entrees={\"data\":{\"$clee1\":\"$secret1\",\"$clee2\":\"$secret2\",\"$clee3\":\"$secret3\"},\"options\":{}}
	curl -X PUT -H "X-Vault-Request: true" -H "X-Vault-Token: $token" -d "$entrees" http://$ip:8200/v1/internal/data/database/config

	#3
	curl -X POST -H "X-Vault-Request: true" -H "X-Vault-Token: $token" -d '{"type":"kubernetes","description":"","config":{"options":null,"default_lease_ttl":"0s","max_lease_ttl":"0s","force_no_cache":false},"local":false,"seal_wrap":false,"external_entropy_access":false,"options":null}' http://$ip:8200/v1/sys/auth/kubernetes

	#4
	curl -X PUT -H "X-Vault-Request: true" -H "X-Vault-Token: $token" -d '{"kubernetes_ca_cert":"-----BEGIN CERTIFICATE-----\nMIIDATCCAemgAwIBAgIJAJx67m2XrThhMA0GCSqGSIb3DQEBCwUAMBcxFTATBgNV\nBAMMDDEwLjE1Mi4xODMuMTAeFw0yMTA0MTEwOTA0NDZaFw0zMTA0MDkwOTA0NDZa\nMBcxFTATBgNVBAMMDDEwLjE1Mi4xODMuMTCCASIwDQYJKoZIhvcNAQEBBQADggEP\nADCCAQoCggEBAKM+EIWdCRtUHVHg4Q+9ySBNXZ3f9qe6YnYwSdMddOCrO3Kb4N1j\nD5wXpFUUI1K8ZbfXjyhgLRXpjK+bv9vcyaXkVaQjKrat5k63oraGroWSu+msuRaf\n1glVt1X9ysiTSKcD26V9g6e1SjB7+hK7JucNweHp9nfJ9elz0avJNPg08ClbEccl\nRA553xnHdesDEdDvnXzo8VMrF2tRNJ/8lnHHTgmk8ZPlRRqw33onH1duAzBpehdd\nR/SX8XkOXDxb92sU1W9qqJOHcWUkVmqz7JwH0sNTyU4+iqdYzcO1uhbzP+A4rapA\nIjt1aJof1SPGMdxVBbZdDlfAjZB4jY98gS0CAwEAAaNQME4wHQYDVR0OBBYEFMvn\nRx2vpJI0WQNWjZbXWat8YoWvMB8GA1UdIwQYMBaAFMvnRx2vpJI0WQNWjZbXWat8\nYoWvMAwGA1UdEwQFMAMBAf8wDQYJKoZIhvcNAQELBQADggEBABKPXocJT7yOgt3a\nioTdoByR4kvn0288n7kc/B9o1tT6wkKmQ/1kKZgwmbja26KBos0HGAFPHSAquTKs\nJl0iCXundJkqSOfEL8UbzPC+7S6JGsSaSKoBtwNHApSIvbVGRHC7idel02KoRxmj\nB06TYbPiVqur0LPW9RP3F/KSENOaCzCeFfxWKXZ0hK3QRLrGb1DXLY2Dyd5edpIS\njMYMMd91t46nxRV8bacdUEbF3XOesgLUEcd6qgN3tKAyf+4GZ/M2ZnaHD7triOYF\nhxcAaggZ0sslWE6kgeQh7qW7vmLqbQBcK7gqyN9nCL/DpW5kDt3t/tutM09V+N1A\nYMY00ts=\n-----END CERTIFICATE-----\n","kubernetes_host":"https://10.152.183.1:443","token_reviewer_jwt":"eyJhbGciOiJSUzI1NiIsImtpZCI6InhYRUdSWDFDYTVGN3dDUkVZRHhMSEFrT19GSW5CS2NYTXN2ZnFwZGEzWFkifQ.eyJpc3MiOiJrdWJlcm5ldGVzL3NlcnZpY2VhY2NvdW50Iiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9uYW1lc3BhY2UiOiJkZWZhdWx0Iiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9zZWNyZXQubmFtZSI6InZhdWx0LXRva2VuLWZxZnR2Iiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9zZXJ2aWNlLWFjY291bnQubmFtZSI6InZhdWx0Iiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9zZXJ2aWNlLWFjY291bnQudWlkIjoiMTkzZmY4MDAtNTRjOS00MjlhLWFlZmUtZWEyOTNhMTUwM2QyIiwic3ViIjoic3lzdGVtOnNlcnZpY2VhY2NvdW50OmRlZmF1bHQ6dmF1bHQifQ.qtZCsTXJ7fGcbeCRPFIhY48l2nyBT_OOauUMym85yQXJd1gFSzEi0KTOyBwiIqO8fMZoowjNrWbjuRfi7hWpwXZm0jsUI9b7fGdTk7PJD8qc95VGPILXZoOYj8sdMkckR3oIyERqsTSzG5TlqPgVNO1LV0QdAZfDWj0UN2sw7SY_Cg3-iIEVVzn-jpE-X2KFcpXmB0iZNA5bktEN3F1DaVPvSAkCPOX8WwVUc5sUyn3rsR_ontZeMgKB97tzouHquShTmYUfRJYFd3EycLXLF-YmbkPx46kWK_bD629njm0ovg1iNVozVKZx4Asqd99Yzt3TxblbYw_jdm1vho9h7Q"}' http://$ip:8200/v1/auth/kubernetes/config

	#5
	curl -X PUT -H "X-Vault-Token: $token" -H "X-Vault-Request: true" -d '{"bound_service_account_names":"internal-app","bound_service_account_namespaces":"default","policies":"internal-app","ttl":"24h"}' http://$ip:8200/v1/auth/kubernetes/role/internal-app
	#6
	curl -X PUT -H "X-Vault-Request: true" -H "X-Vault-Token: $token" -d '{"policy":"path \"internal/data/database/config\" {\n  capabilities = [\"read\"]\n}\n"}' http://$ip:8200/v1/sys/policies/acl/internal-app

elif [ "$1" == "stop" ]; then
	helm uninstall vault

elif [ "$1" == "start" ]; then
	helm install vault helm-charts/vault

elif [ "$1" == "status" ]; then
	kube exec -it vault-0 -- vault status

elif [ "$1" == "deploy" ]; then
	# stop et start
	helm uninstall vault
	helm install vault hashicorp/vault

	## desceller vault (pour chaque unseal key [unseal key])
	kube exec -it vault-0 -- vault operator unseal $key1
	kube exec -it vault-0 -- vault operator unseal $key2
	kube exec -it vault-0 -- vault operator unseal $key3
	
	# set
	ip=$(kube describe service/vault | grep IP: | sed 's/IP:\s\+//')

	#1
	curl -X POST -H "X-Vault-Request: true" -H "X-Vault-Token: $token" -d '{"type":"kv-v2","description":"","config":{"options":null,"default_lease_ttl":"0s","max_lease_ttl":"0s","force_no_cache":false},"local":false,"seal_wrap":false,"external_entropy_access":false,"options":null}' http://$ip:8200/v1/sys/mounts/internal

	#2
	entrees={\"data\":{\"$clee1\":\"$secret1\",\"$clee2\":\"$secret2\",\"$clee3\":\"$secret3\"},\"options\":{}}
	curl -X PUT -H "X-Vault-Request: true" -H "X-Vault-Token: $token" -d "$entrees" http://$ip:8200/v1/internal/data/database/config

	#3
	curl -X POST -H "X-Vault-Request: true" -H "X-Vault-Token: $token" -d '{"type":"kubernetes","description":"","config":{"options":null,"default_lease_ttl":"0s","max_lease_ttl":"0s","force_no_cache":false},"local":false,"seal_wrap":false,"external_entropy_access":false,"options":null}' http://$ip:8200/v1/sys/auth/kubernetes

	#4
	curl -X PUT -H "X-Vault-Request: true" -H "X-Vault-Token: $token" -d '{"kubernetes_ca_cert":"-----BEGIN CERTIFICATE-----\nMIIDATCCAemgAwIBAgIJAJx67m2XrThhMA0GCSqGSIb3DQEBCwUAMBcxFTATBgNV\nBAMMDDEwLjE1Mi4xODMuMTAeFw0yMTA0MTEwOTA0NDZaFw0zMTA0MDkwOTA0NDZa\nMBcxFTATBgNVBAMMDDEwLjE1Mi4xODMuMTCCASIwDQYJKoZIhvcNAQEBBQADggEP\nADCCAQoCggEBAKM+EIWdCRtUHVHg4Q+9ySBNXZ3f9qe6YnYwSdMddOCrO3Kb4N1j\nD5wXpFUUI1K8ZbfXjyhgLRXpjK+bv9vcyaXkVaQjKrat5k63oraGroWSu+msuRaf\n1glVt1X9ysiTSKcD26V9g6e1SjB7+hK7JucNweHp9nfJ9elz0avJNPg08ClbEccl\nRA553xnHdesDEdDvnXzo8VMrF2tRNJ/8lnHHTgmk8ZPlRRqw33onH1duAzBpehdd\nR/SX8XkOXDxb92sU1W9qqJOHcWUkVmqz7JwH0sNTyU4+iqdYzcO1uhbzP+A4rapA\nIjt1aJof1SPGMdxVBbZdDlfAjZB4jY98gS0CAwEAAaNQME4wHQYDVR0OBBYEFMvn\nRx2vpJI0WQNWjZbXWat8YoWvMB8GA1UdIwQYMBaAFMvnRx2vpJI0WQNWjZbXWat8\nYoWvMAwGA1UdEwQFMAMBAf8wDQYJKoZIhvcNAQELBQADggEBABKPXocJT7yOgt3a\nioTdoByR4kvn0288n7kc/B9o1tT6wkKmQ/1kKZgwmbja26KBos0HGAFPHSAquTKs\nJl0iCXundJkqSOfEL8UbzPC+7S6JGsSaSKoBtwNHApSIvbVGRHC7idel02KoRxmj\nB06TYbPiVqur0LPW9RP3F/KSENOaCzCeFfxWKXZ0hK3QRLrGb1DXLY2Dyd5edpIS\njMYMMd91t46nxRV8bacdUEbF3XOesgLUEcd6qgN3tKAyf+4GZ/M2ZnaHD7triOYF\nhxcAaggZ0sslWE6kgeQh7qW7vmLqbQBcK7gqyN9nCL/DpW5kDt3t/tutM09V+N1A\nYMY00ts=\n-----END CERTIFICATE-----\n","kubernetes_host":"https://10.152.183.1:443","token_reviewer_jwt":"eyJhbGciOiJSUzI1NiIsImtpZCI6InhYRUdSWDFDYTVGN3dDUkVZRHhMSEFrT19GSW5CS2NYTXN2ZnFwZGEzWFkifQ.eyJpc3MiOiJrdWJlcm5ldGVzL3NlcnZpY2VhY2NvdW50Iiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9uYW1lc3BhY2UiOiJkZWZhdWx0Iiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9zZWNyZXQubmFtZSI6InZhdWx0LXRva2VuLWZxZnR2Iiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9zZXJ2aWNlLWFjY291bnQubmFtZSI6InZhdWx0Iiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9zZXJ2aWNlLWFjY291bnQudWlkIjoiMTkzZmY4MDAtNTRjOS00MjlhLWFlZmUtZWEyOTNhMTUwM2QyIiwic3ViIjoic3lzdGVtOnNlcnZpY2VhY2NvdW50OmRlZmF1bHQ6dmF1bHQifQ.qtZCsTXJ7fGcbeCRPFIhY48l2nyBT_OOauUMym85yQXJd1gFSzEi0KTOyBwiIqO8fMZoowjNrWbjuRfi7hWpwXZm0jsUI9b7fGdTk7PJD8qc95VGPILXZoOYj8sdMkckR3oIyERqsTSzG5TlqPgVNO1LV0QdAZfDWj0UN2sw7SY_Cg3-iIEVVzn-jpE-X2KFcpXmB0iZNA5bktEN3F1DaVPvSAkCPOX8WwVUc5sUyn3rsR_ontZeMgKB97tzouHquShTmYUfRJYFd3EycLXLF-YmbkPx46kWK_bD629njm0ovg1iNVozVKZx4Asqd99Yzt3TxblbYw_jdm1vho9h7Q"}' http://10.152.183.64:8200/v1/auth/kubernetes/config

	#5
	curl -X PUT -H "X-Vault-Token: $token" -H "X-Vault-Request: true" -d '{"bound_service_account_names":"internal-app","bound_service_account_namespaces":"default","policies":"internal-app","ttl":"24h"}' http://$ip:8200/v1/auth/kubernetes/role/internal-app
	#6
	curl -X PUT -H "X-Vault-Request: true" -H "X-Vault-Token: $token" -d '{"policy":"path \"internal/data/database/config\" {\n  capabilities = [\"read\"]\n}\n"}' http://$ip:8200/v1/sys/policies/acl/internal-app
	
else
	echo "
	init => à faire une fois (initialise et install vault)
	deploy => lance vault avec les données necessaires
	start => lance vault
	stop  => arrête vault
	unseal => à chaque helm install (descelle vault)
	status  => vérifie le status de vault
	set => ajoute les secrets créés
	[void] => affiche cette aide
"
fi

