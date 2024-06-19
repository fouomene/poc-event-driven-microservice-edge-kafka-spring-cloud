# Exercice Promo 10000codeurs 2024

[Fall Omar](https://github.com/omar218)
[Jovy Nedved Monka - Kanda](https://github.com/jovyzabel)
[Warda Boubaker](https://github.com/wardabk)
[Oumou Sow](https://github.com/glowriousmou)
[Vianney YOVO](https://github.com/vianneyyovo)
[Sidonie DJUISSI FOHOUO](https://github.com/sidonieGit)
[Fabrice NANKAM](https://github.com/fabricenankam)
[VODOUNNOU Nicodème Sèlomin](https://github.com/NicoSelomin)
[Moussa TAMBA](https://github.com/tambamoussa)
[ABOUBAKAR](https://github.com/Aaboubakar)
[Hothia DIAO](https://github.com/hothiadiao)
[FatimaDianfou](https://github.com/FatimaDianfou)
[Aicha BENJDIR](https://github.com/AichaBenjdir)
[Cheick Oumar DIAKITE](https://github.com/Oumar72-HUB)
[AMOUSSOUGBO Anani Serge](https://github.com/SergeCodeur)
[Ramiro Kaffo](https://github.com/Ramirokaffo)
[Rodrigue Nzomo](https://github.com/RodrigueNzomo)
[Jalil Fonda](https://github.com/JalilGafar)
[Yvon MENSAH](https://github.com/Yvon-30)
[Nsengimana François](https://github.com/IMANA47)
[Quirin DOSSOU](https://github.com/QuirinD1)
[Carles De Souza](https://github.com/carlesdesouza)
 

# Exercice Promo 10000codeurs 2023

[Joel Aholou](https://github.com/ahjoel)
[Borel Njeunkwe](https://github.com/Lerob28)
[Jean pierre momo](https://github.com/MomoJeanPierre)
[Serges YAMAKO](https://github.com/yamakoserge)
[Vignon DOSSOU](https://github.com/vignondossou)
[Abderrahmane ZAHI](https://github.com/abderrahmanez)
[Paul MBUYI](https://github.com/PaulMbuyi)
[Farimata DOUARE](https://github.com/Fatim94)
[Jean Apotre AIKOU](https://github.com/jeanapotreaikou)
[Farimata DOUARE](https://github.com/Fatim94) 
[Latifatou IYALI](https://github.com/latifahamraou)
[paul damien](https://github.com/pauldamien) 
[LEKEUFACK TAMEZE](https://github.com/Lekeufack-Tameze)
[OUFFOUET Patrick](https://github.com/ouffouetpatrick)

# poc-event-driven-microservice-edge-kafka-spring-cloud



https://www.slideshare.net/fouomene/migration-dune-architecture-microservice-vers-une-architecture-eventdriven-microservice-avec-kafka

Architecture
![alt text](https://fouomene.com/event-driven-microservice-architecture.jpg)

![alt text](https://fouomene.com/microservice-broker-kafka.jpg)

Mise en oeuvre de l'architechture Event-Driven Microservice avec Apache Kafka, Spring Cloud et ses Edge Microservices, pour une communicaion service to broker à la place de Service to Service:

- **Spring Cloud Bootstrap** (e.g. Bootstrap context and @RefreshScope)

- **Spring Cloud Config** : Se positionne comme serveur de distribution des fichiers de configuration.
	- **Config Server**
	- **Config Client**
	
- **Spring Cloud Netflix** 
	- **Discovery** : Permet de découvrir les Microservice sur notre serveur. C’est aussi un outil clé pour la gestion des Microservices.
		- **Eureka Server**
		- **Eureka Client**
	- **Load Balancer**
		- **Ribbon** (config et dependance commenté) : Équilibrer les requêtes entre plusieurs instances pour éviter une surcharge d’un serveur
	- **Circuit Breaker** : Définit un ensemble de seuils qui, une fois dépassés, arrêteront l'exécution d'un bloc de code. 
		- **Hystrix** : Une API pour la résilience d’applications.
	
- **Spring Cloud Routing**
    - **Gateway** : Le point d’entrée unique pour les API et Microservices.
	- **OpenFeign** : Faire communiquer (synchrone) les microservices grâce à Feign.
	
- **Spring Cloud Load Balancer** : Équilibrer les requêtes entre plusieurs instances pour éviter une surcharge d’un serveur
	
- **Spring Cloud Observability**
    - **Sleuth** : Permet de donner des ID uniques à chaque requête, c'est 
	- **Zipkin** Client : permet exposer les traces vers Zipkin Server.
	
- **Spring Boot Actuator** : expose une API qui donne des informations sur le microservice concerné, mais ne propose pas d'interface graphique.

- **Zipkin Server** : permet de tracer les requêtes de service en service uniquement si ces services intègrent ses dépendances.


# Step 1 : Télécharger et Démarrer Zipkin Server
	curl -sSL https://zipkin.io/quickstart.sh | bash -s
	java -jar zipkin.jar


# Step 2 : Télécharger kafka

Download https://kafka.apache.org the latest Kafka release and extract it:

    $tar -xzf kafka_2.13-3.2.0.tgz

    $cd kafka_2.13-3.2.0


# Step 3 : Démarrer kafka

NOTE: Your local environment must have Java 8+ installed.

Run the following commands in order to start all services in the correct order:

- **Start the ZooKeeper service**

-Note: Soon, ZooKeeper will no longer be required by Apache Kafka.

    $bin/zookeeper-server-start.sh config/zookeeper.properties

or window

    $.\bin\windows\zookeeper-server-start.bat .\config\zookeeper.properties

Open another terminal session and run:

- **Start the Kafka broker service**


    $bin/kafka-server-start.sh config/server.properties

or window

    $.\bin\windows\kafka-server-start.bat .\config\server.properties

Once all services have successfully launched, you will have a basic Kafka environment running and ready to use.

# Step 4 : Démarrer les différents microservices.
	mvn spring-boot:run
Ordre recommandé
- config-server
- eureka-server
- springcloudgateway-server
- microservice-produits (deux instances Ex port 8001,8011)
- microservice-commandes-consumer
- microservice-paiement-producer
- clientui


Eureka Server
![alt text](https://fouomene.com/eureka-server.jpg)

Zipkin Server
![alt text](https://fouomene.com/zipkin-kafka1.jpg)
![alt text](https://fouomene.com/zipkin-kafka2.jpg)

Microservice ClientUI
![alt text](https://fouomene.com/minicommerce.jpg) 




