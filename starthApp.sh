#!/bin/bash

echo "Killing the previous running processes: $( pgrep holochain )"
pgrep holochain | xargs kill -15
cd ../smartchainhApp/hApp
nohup echo "timon" | holochain -c ../conductor-config.toml > hApp.log &
echo "The new process PID: $( pgrep holochain )"
