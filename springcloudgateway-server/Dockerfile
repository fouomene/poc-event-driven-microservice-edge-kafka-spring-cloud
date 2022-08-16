FROM openjdk:11
MAINTAINER fouomene.com
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} springcloudgateway-server.jar
ENTRYPOINT ["java","-jar","/springcloudgateway-server.jar"]