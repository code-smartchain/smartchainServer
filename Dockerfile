FROM openjdk:10-jre-slim
    WORKDIR /home
    COPY server/build/libs .
    CMD ["java", "-jar", "server-0.0.1-SNAPSHOT.jar"]