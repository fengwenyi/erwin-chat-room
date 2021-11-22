#!/bin/bash
version=1.1.3
mvn clean package -DskipTests
docker build -t fengwenyi/erwin-chat-room:$version .
docker tag fengwenyi/erwin-chat-room:$version fengwenyi/erwin-chat-room:$version
docker push fengwenyi/erwin-chat-room:$version
git tag -a $version -m "v$version"
git push origin $version