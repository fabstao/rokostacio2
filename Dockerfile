## Stage 1 : build with maven builder image with native capabilities
FROM quay.io/quarkus/centos-quarkus-maven:19.3.1-java11 AS build
#FROM quay.io/quarkus/centos-quarkus-maven:latest AS build
COPY pom.xml /project/pom.xml
ADD pom.xml /project/pom.xml
RUN echo "hola" > /project/test
RUN ls /project
RUN cat /project/test
RUN mvn -f /project/pom.xml -B de.qaware.maven:go-offline-maven-plugin:1.2.5:resolve-dependencies
COPY src /project/src
USER root
RUN chown -R quarkus /project
RUN chown -R quarkus /project/*
USER quarkus
RUN mvn -f /project/pom.xml -Pnative clean package

## Stage 2 : create the docker final image
FROM registry.access.redhat.com/ubi8/ubi-minimal
WORKDIR /work/
COPY --from=build /project/target/*-runner /work/application

# set up permissions for user `1001`
RUN chmod 775 /work /work/application \
  && chown -R 1001 /work \
  && chmod -R "g+rwX" /work \
  && chown -R 1001:root /work

EXPOSE 8080
USER 1001

CMD ["./application", "-Dquarkus.http.host=0.0.0.0"]
