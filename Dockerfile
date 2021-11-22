FROM openjdk:17-jdk-alpine
MAINTAINER Erwin Feng xfsy_2015@163.com

#ENV active = 'dev'

COPY target/erwin-chat-room-*.jar /erwin-chat-room.jar
ENTRYPOINT ["sh", "-c", "java -jar erwin-chat-room.jar --spring.profile.active=$active"]