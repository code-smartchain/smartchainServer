#!/bin/bash

cd ../smartchainhApp/hApp
echo "Addr is $1; Port is $2; BootstrapNode is $3; File to write to is $4"
> ../conductor-config2.toml && addr=$1 port=$2 node=$3 n3h_persistence_path=$5 envsubst < ../conductor-config-template.toml >> ../conductor-config2.toml
nohup echo "A)=B&7" | holochain -c ../conductor-config2.toml > $4 2>&1 &
sleep 10
echo "The new process PID: $( pgrep holochain )"
