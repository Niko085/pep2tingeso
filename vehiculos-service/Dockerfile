FROM openjdk:17
ARG JAR_FILE=./target/*.jar
COPY ${JAR_FILE} vehiculos-service-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/vehiculos-service-0.0.1-SNAPSHOT.jar"]