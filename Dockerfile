FROM oraclejdk8
    WORKDIR /home
    COPY server/build/libs .
    CMD ["java", "-jar", "server-0.0.1-SNAPSHOT.jar"]