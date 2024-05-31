FROM openjdk:17
ARG JAR_FILE=./target/*.jar
COPY ${JAR_FILE} valor-reparaciones-service.jar
ENTRYPOINT ["java","-jar","/valor-reparaciones-service.jar"]