#!/bin/bash

WILDFLY_HOME=/home/ricardo/Projetos-Ricardo/wildfly-15.0.0.Final

if [ ! -d $WILDFLY_HOME ]; then
  echo 'Wildfly home directory not found!'
  exit
fi

echo "Cleaning up deployments..."
rm -rf $WILDFLY_HOME/standalone/deployments/*

echo "Deploying new version..."
cp target/wildapp.war $WILDFLY_HOME/standalone/deployments/

echo "Starting WILDFLY server..."
$WILDFLY_HOME/bin/standalone.sh --debug