FROM maven:3.8.1-jdk-8 AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package

FROM adoptopenjdk:8-jre
COPY --from=build /home/app/target/dropwizard-example-mvn-1.0-SNAPSHOT.jar .
COPY config/jmx-test.yml .
EXPOSE 1088
ENTRYPOINT ["java", "-Dcom.sun.management.jmxremote", "-Dcom.sun.management.jmxremote.port=1088", "-Dcom.sun.management.jmxremote.authenticate=false", "-Dcom.sun.management.jmxremote.ssl=false", "-jar", "dropwizard-example-mvn-1.0-SNAPSHOT.jar", "server", "jmx-test.yml"]
