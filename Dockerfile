FROM amazoncorretto:11-alpine-jdk
COPY target/test-0.0.1-SNAPSHOT.jar .
ENTRYPOINT ["java","-jar","test-0.0.1-SNAPSHOT.jar"]

