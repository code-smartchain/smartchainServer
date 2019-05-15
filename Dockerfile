FROM adoptopenjdk/openjdk8:latest
    WORKDIR /home
    COPY server/build/libs ./server
    ADD  ./hApp ./hApp
    ADD  ./server/animals.txt ./server
    ADD  ./server/starthApp.sh ./server
    ADD  ./server/startInstance.sh ./server
    CMD ["sh", "./server/starthApp.sh"]