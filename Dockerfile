FROM amazoncorretto:11-alpine-jdk
COPY target/test-0.0.1-SNAPSHOT.jar test-app
ENTRYPOINT [ "java","-jar","/testNexosApp.jar" ]
CMD ["java", "-jar", "test-app.jar"]