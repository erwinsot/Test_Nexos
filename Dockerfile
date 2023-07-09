FROM amazoncorretto:11-alpine-jdk
MAINTAINER erwin
COPY target/test-0.0.1-SNAPSHOT.jar test-app
ENTRYPOINT [ "java","-jar","/test-app.jar" ]