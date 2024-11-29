# build stage
FROM gradle:8-jdk21-alpine AS build-stage

ENV INSTALL_PATH=/app

WORKDIR ${INSTALL_PATH}
COPY . ${INSTALL_PATH}

RUN gradle clean build assemble

# production stage
FROM openjdk:21-jdk AS production-stage
LABEL maintainer="ogsugoi@gmail.com"

ARG JAR_FILE=/app/main/build/libs/*-SNAPSHOT.jar
COPY --from=build-stage ${JAR_FILE} app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar","/app.jar"]
