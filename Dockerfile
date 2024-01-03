FROM amazoncorretto:17.0.5


CPOY  target//HHUserInfo-0.0.1-SNAPSHOT-exec.jar  app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
