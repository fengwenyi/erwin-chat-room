#!/bin/bash
version=2.0.0
mvn clean package -DskipTests
docker build -t fengwenyi/erwin-chat-room:$version .
docker tag fengwenyi/erwin-chat-room:$version fengwenyi/erwin-chat-room:$version
docker push fengwenyi/erwin-chat-room:$version
git tag -a $version -m "v$version"
git push origin $version