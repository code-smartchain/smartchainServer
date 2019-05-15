FROM adoptopenjdk/openjdk8:latest
    RUN  apt-get update \
         && apt-get install -y wget \
         && rm -rf /var/lib/apt/lists/*
    WORKDIR /home
    COPY server/build/libs ./server
    ADD  ./hApp ./hApp
    ADD  ./server/configDocker.sh ./server
    ADD  ./server/starthApp.sh ./server
    ADD  ./server/startInstance.sh ./server
    CMD ["sh", "./server/starthApp.sh"]
    # CMD ["java", "-jar", "./server/server-0.0.1-SNAPSHOT.jar"]