#!/usr/bin/env bash

MAVEN_VERSION=${1:-3.5.2}
docker build --build-arg MAVEN_VERSION=${MAVEN_VERSION} uid=${UID} gid=$(id -g jenkins) -t ayoubensalem/spring:dev  .