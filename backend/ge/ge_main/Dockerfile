FROM eclipse-temurin:17-jdk as build
ARG INSTALL_PATH=/home/app
WORKDIR ${INSTALL_PATH}

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src

RUN \
    apt-get update && \
    apt-get install dos2unix -y && \
    cd ${INSTALL_PATH}/ && \
    dos2unix mvnw && \
    sleep 1
RUN ./mvnw install -DskipTests
RUN mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)

FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
ARG DEPENDENCY=/home/app/target/dependency
COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app
ENTRYPOINT ["java","-cp","app:app/lib/*","com.gi.hrm.GiHrmBeApplication"]
