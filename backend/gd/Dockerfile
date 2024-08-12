# build stage
FROM gradle:jdk21-alpine AS build-stage
WORKDIR /app
COPY . .
RUN gradle build

# production stage
FROM openjdk:21-jdk AS production-stage
LABEL maintainer="ogsugoi@gmail.com"

ARG JAR_FILE=/app/build/libs/*.jar
COPY --from=build-stage ${JAR_FILE} app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]
