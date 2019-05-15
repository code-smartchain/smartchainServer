FROM adoptopenjdk/openjdk8:latest
    WORKDIR /home
    COPY server/build/libs ./server
    ADD  ./server/animal.txt ./server
    ADD  ./server/starthApp.sh ./server
    ADD  ./server/startInstance.sh ./server
    CMD ["java", "-jar", "server-0.0.1-SNAPSHOT.jar"]