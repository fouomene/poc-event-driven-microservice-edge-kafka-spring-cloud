FROM openjdk:11
MAINTAINER fouomene.com
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} microservice-commandes-consumer.jar
ENTRYPOINT ["java","-jar","/microservice-commandes-consumer.jar"]