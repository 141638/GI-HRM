# build stage
FROM maven:3.9-eclipse-temurin-21-alpine as build-stage
WORKDIR /app
COPY pom.xml /app/pom.xml
COPY src /app/src
RUN mvn -f /app/pom.xml clean package
#RUN mvn dependency:go-offline -B

# production stage
FROM openjdk:21-jdk as production-stage
LABEL maintainer="ogsugoi@gmail.com"

ARG JAR_FILE=/app/target/*.jar
COPY --from=build-stage ${JAR_FILE} app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]
