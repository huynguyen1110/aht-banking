FROM openjdk:17

EXPOSE 8088

COPY auth-service/target/auth-service-0.0.1-SNAPSHOT.jar auth-service-0.0.1-SNAPSHOT.jar

ENTRYPOINT [ "java", "-jar", "auth-service-0.0.1-SNAPSHOT.jar" ]