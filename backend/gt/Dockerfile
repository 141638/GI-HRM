# build stage
FROM maven:3.8.4-openjdk-17-slim as build-stage
WORKDIR /app
COPY pom.xml /app/pom.xml
COPY gt_* /app
RUN mvn -f /app/pom.xml clean package
RUN mvn -f /app/pom.xml install clean

# production stage
FROM openjdk:17-jdk-alpine as production-stage
LABEL maintainer="ogsugoi@gmail.com"

ARG JAR_FILE=target/GI-GATEWAY-0.0.1-SNAPSHOT.jar
COPY --from=build-stage ${JAR_FILE} app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]
