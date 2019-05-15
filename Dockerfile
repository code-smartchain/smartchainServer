FROM adoptopenjdk/openjdk8:latest
    WORKDIR /home
    COPY ../hApp ../
    COPY server/build/libs .
    COPY server/animal.txt .
    COPY server/starthApp.sh .
    COPY server/startInstance.sh .
    CMD ["java", "-jar", "server-0.0.1-SNAPSHOT.jar"]