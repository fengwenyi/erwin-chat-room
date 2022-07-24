#!/bin/bash
version=`awk '/<version>[^<]+<\/version>/{gsub(/<version>|<\/version>/,"",$1);print $1;exit;}' pom.xml`
echo $version
# jdk17
mvn clean package -DskipTests
docker build -t fengwenyi/erwin-chat-room .
docker tag fengwenyi/erwin-chat-room fengwenyi/erwin-chat-room
docker push fengwenyi/erwin-chat-room
git tag -a $version -m "v$version"
git push origin $version