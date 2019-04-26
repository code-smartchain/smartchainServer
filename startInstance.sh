#!/bin/bash

cd ../smartchainhApp/hApp
> ../conductor-config2.toml && addr=$1 port=$2 node=$3 n3h_persistence_path=$5 envsubst < ../conductor-config-template.toml >> ../conductor-config2.toml
nohup echo "A)=B&7" | holochain -c ../conductor-config2.toml > $4 2>&1 &
sleep 10
echo "The new process PID: $( pgrep holochain )"
