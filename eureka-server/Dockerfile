FROM openjdk:17

EXPOSE 8761

COPY eureka-server/target/eureka-server-0.0.1-SNAPSHOT.jar eureka-server-0.0.1-SNAPSHOT.jar

ENTRYPOINT [ "java", "-jar", "eureka-server-0.0.1-SNAPSHOT.jar" ]