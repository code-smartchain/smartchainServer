#!/bin/bash
echo "Configuring Docker"
chmod +x ./server/configDocker.sh
sh ./server/configDocker.sh

echo "Killing the previous running processes: $( pgrep holochain )"
pgrep holochain | xargs kill -15
pgrep node | xargs kill -15
pwd 
ls -a
cd ./hApp
nohup echo "A)=B&7" | holochain -c ../conductor-config.toml > hApp.log 2>&1 &
sleep 10
echo "The new process PID: $( pgrep holochain )"
echo "Starting Server ..."
cd ../server
java -jar server-0.0.1-SNAPSHOT.jar
