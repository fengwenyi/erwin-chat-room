FROM openjdk:17-jdk-alpine
MAINTAINER Erwin Feng xfsy_2015@163.com

ENV active = 'dev'
ENV TZ=Asia/Shanghai

RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

COPY target/erwin-chat-room-*.jar /erwin-chat-room.jar
ENTRYPOINT ["sh", "-c", "java -jar erwin-chat-room.jar --spring.profiles.active=$active"]