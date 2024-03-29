FROM openjdk:17

EXPOSE 8091

COPY transaction-service/target/transaction-service-0.0.1-SNAPSHOT.jar transaction-service-0.0.1-SNAPSHOT.jar

ENTRYPOINT [ "java", "-jar", "transaction-service-0.0.1-SNAPSHOT.jar" ]