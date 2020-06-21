## Stage 1 : build with maven builder image with native capabilities
FROM quay.io/quarkus/centos-quarkus-maven:19.3.1-java11 AS build
COPY pom.xml /project/pom.xml
RUN mvn -f /project/pom.xml -B de.qaware.maven:go-offline-maven-plugin:1.2.5:resolve-dependencies
COPY src /project/src
USER root
RUN chown -R quarkus /project
RUN chown -R quarkus /project/*
USER quarkus
RUN mvn -X -f /project/pom.xml -Pnative clean package

## Stage 2 : create the docker final image
FROM registry.access.redhat.com/ubi8/ubi-minimal

ENV MONGOPORT 27017
ENV QUARKUS_HTTP_PORT 8080
ENV QUARKUS_HTTP_TESTPORT 8189
ENV QUARKUS_MONGODB_HOSTS 127.0.0.1:$MONGOPORT
ENV QUARKUS_MONGODB_DATABASE rokostaging

WORKDIR /work/
COPY --from=build /project/target/*-runner /work/application

# set up permissions for user `1001`
RUN chmod 775 /work /work/application \
  && chown -R 1001 /work \
  && chmod -R "g+rwX" /work \
  && chown -R 1001:root /work

EXPOSE $QUARKUS_HTTP_PORT
EXPOSE $QUARKUS_HTTP_TESTPORT
USER 1001

CMD ["sh","-c","./application","-Dquarkus.http.port=$QUARKUS_HTTP_PORT", "-Dquarkus.http.test-port=$QUARKUS_HTTP_TESTPORT", "-Dquarkus.http.host=0.0.0.0","-Dquarkus.mongodb.hosts=$QUARKUS_MONGODB_HOSTS","-Dquarkus.mongodb.database=$QUARKUS_MONGODB_DATABASE"]
