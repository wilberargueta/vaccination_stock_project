FROM openjdk:11
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
COPY application_docker.yml application.yml
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]