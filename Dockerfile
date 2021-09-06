FROM openjdk:8-jdk-alpine
MAINTAINER Erwin Feng xfsy_2015@163.com

COPY target/erwin-chat-room-*.jar /erwin-chat-room.jar
ENTRYPOINT ["java", "-jar", "/erwin-chat-room.jar"]