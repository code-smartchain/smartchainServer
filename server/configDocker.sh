#!/bin/bash
echo "Docker Setup running ..."
# Download desired version of Holochain
wget https://github.com/holochain/holochain-rust/releases/download/v0.0.9-alpha/conductor-v0.0.9-alpha-x86_64-ubuntu-linux-gnu.tar.gz -O /tmp/conductor-v0.0.9-alpha-x86_64-ubuntu-linux-gnu.tar.gz
tar -xvf /tmp/conductor-v0.0.9-alpha-x86_64-ubuntu-linux-gnu.tar.gz
mv conductor-v0.0.9-alpha-x86_64-unknown-linux-gnu holochain
export PATH=$PWD/holochain:$PATH
. $HOME/.profile
holochain --version

# Download desired version of Rust
curl https://sh.rustup.rs -sSf | bash -s -- -y
. $HOME/.cargo/env
rustup toolchain install nightly-2019-01-24
rustup default nightly-2019-01-24
rustup target add wasm32-unknown-unknown --toolchain nightly-2019-01-24