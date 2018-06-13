#!/usr/bin/env bash

java -Djava.security.egd=file:/dev/./urandom -jar stock.jar
java -Djava.security.egd=file:/dev/./urandom -jar catalog.jar server app-config.yml
java -Djava.security.egd=file:/dev/./urandom -jar front.jar

