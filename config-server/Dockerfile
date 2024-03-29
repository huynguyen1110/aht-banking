FROM openjdk:17

EXPOSE 9005

COPY config-server/target/config-server-0.0.1-SNAPSHOT.jar config-server-0.0.1-SNAPSHOT.jar

ENTRYPOINT [ "java", "-jar", "config-server-0.0.1-SNAPSHOT.jar" ]