#!/bin/bash

echo "Killing the previous running processes: $( pgrep holochain )"
pgrep holochain | xargs kill -15
pgrep node | xargs kill -15
cd ../hApp
nohup echo "A)=B&7" | holochain -c ../conductor-config.toml > hApp.log 2>&1 &
sleep 10
echo "The new process PID: $( pgrep holochain )"