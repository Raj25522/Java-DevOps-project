FROM eclipse-temurin:17-jre

WORKDIR /app

COPY target/java-webapp-0.0.1-SNAPSHOT.war app.war

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "app.war"]
