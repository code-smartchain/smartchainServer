FROM openjdk:10-jre-slim
    WORKDIR /home
    COPY build/libs .
    CMD ["java", "-jar", "tracking-ingestion-service.jar"]