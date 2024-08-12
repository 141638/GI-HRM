# build stage
FROM maven:3.9-eclipse-temurin-21-alpine as build-stage
RUN mkdir -p /app
WORKDIR /app
COPY gv_lib /app/gv_lib
COPY gv_main /app/gv_main
COPY pom.xml /app/pom.xml
RUN mvn -f /app/pom.xml clean package

# production stage
FROM openjdk:21-jdk as production-stage
LABEL maintainer="ogsugoi@gmail.com"

ARG JAR_FILE=/app/gv_main/target/*.jar
COPY --from=build-stage ${JAR_FILE} /app/gv_main/app.jar

EXPOSE 8080
ENTRYPOINT java -jar /app/gv_main/app.jar
