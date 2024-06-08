FROM openjdk:17
ARG JAR_FILE=./target/*.jar
COPY ${JAR_FILE} reportes-service-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/reportes-service-0.0.1-SNAPSHOT.jar"]