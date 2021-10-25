#!/bin/bash
mvn clean package -DskipTests
docker build -t fengwenyi/erwin-chat-room:1.1.2 .
docker tag fengwenyi/erwin-chat-room:1.1.2 fengwenyi/erwin-chat-room:1.1.2
docker push fengwenyi/erwin-chat-room:1.1.2
git tag -a 1.1.2 -m 'v1.1.2'
git push origin 1.1.2