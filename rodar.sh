#!/bin/bash

#WILDFLY_HOME=/home/ricardo/Projects/wildfly-15.0.1.Final
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
export JAVA_OPTS="-Xms64M -Xmx2048M -XX:MetaspaceSize=96M -XX:MaxMetaspaceSize=512m -Djava.net.preferIPv4Stack=true -Djboss.modules.system.pkgs=org.jboss.byteman -Dfile.encoding=UTF-8"
$WILDFLY_HOME/bin/standalone.sh --debug -Djboss.server.config.dir=$(pwd)/wildfly-cfg -Djboss.server.log.dir=$(pwd)