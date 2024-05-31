FROM openjdk:17
ARG JAR_FILE=./target/*.jar
COPY ${JAR_FILE} vehiculos-service.jar
ENTRYPOINT ["java","-jar","/vehiculos-service.jar"]