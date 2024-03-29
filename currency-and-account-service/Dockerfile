FROM openjdk:17

EXPOSE 8090

COPY currency-and-account-service/target/currency-and-account-service-0.0.1-SNAPSHOT.jar currency-and-account-service-0.0.1-SNAPSHOT.jar

ENTRYPOINT [ "java", "-jar", "currency-and-account-service-0.0.1-SNAPSHOT.jar" ]