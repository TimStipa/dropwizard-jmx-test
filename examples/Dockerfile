FROM maven:3.8.1-jdk-8 AS build
COPY ExampleRunner.java JmxClient.java ./
RUN javac ExampleRunner.java JmxClient.java

FROM adoptopenjdk:8-jre
COPY --from=build ExampleRunner.class JmxClient.class ./
ENTRYPOINT ["java", "-cp", ".", "ExampleRunner"]
