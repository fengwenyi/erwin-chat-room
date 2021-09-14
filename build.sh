#!/bin/bash
mvn clean package -DskipTests
docker build -t fengwenyi/erwin-chat-room:1.1.1 .