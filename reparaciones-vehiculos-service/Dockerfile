FROM openjdk:17
ARG JAR_FILE=./target/*.jar
COPY ${JAR_FILE} reparaciones-vehiculos-service.jar
ENTRYPOINT ["java","-jar","/reparaciones-vehiculos-service.jar"]