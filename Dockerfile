FROM adoptopenjdk/openjdk8:latest
    WORKDIR /home
    COPY server/build/libs ./server
    COPY server/animal.txt ./server
    COPY server/starthApp.sh ./server
    COPY server/startInstance.sh ./server
    CMD ["java", "-jar", "server-0.0.1-SNAPSHOT.jar"]