FROM openjdk:11
MAINTAINER fouomene.com
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} microservice-clientui.jar
ENTRYPOINT ["java","-jar","/microservice-clientui.jar"]