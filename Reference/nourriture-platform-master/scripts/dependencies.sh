#!/bin/sh
# Environment dependencies, i.e. anything that has to be installed
# system-wide to configure and run the application.
#
# NOTE: Script assumes OS is Ubuntu 14.04 or newer.
#
# Niels Søholm (2014-10-11)

apt-get update

# Install Node.js dependencies
apt-get -y install g++ curl libssl-dev apache2-utils make
apt-get -y install git-core

# Install Node.js (Webserver/Engine)
mkdir tmp
cd tmp
git clone git://github.com/ry/node.git
cd node
git checkout v0.10.32-release
./configure
make
make install
cd ../..

# Install Node Package Manager (NPM, to manage 3rd-party JS dependencies)
cd tmp
wget http://npmjs.org/install.sh
sh ./install.sh
cd ..

# Install node utility for registering services (i.e. run forever)
npm install forever -g
apt-get -y install unzip

# Install MongoDB (database)
sudo apt-key adv --keyserver hkp://keyserver.ubuntu.com:80 --recv 7F0CEB10
echo 'deb http://downloads-distro.mongodb.org/repo/ubuntu-upstart dist 10gen' | sudo tee /etc/apt/sources.list.d/mongodb.list
sudo apt-get update
sudo apt-get install -y mongodb-org
sudo service mongod start

# Clean up
rm -r tmp