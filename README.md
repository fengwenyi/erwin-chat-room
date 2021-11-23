# Chat Room

## 快速开始

```shell
docker pull fengwenyi/erwin-chat-room:1.1.3

docker run -d -p 8080:8080 --name erwin-chat-room fengwenyi/erwin-chat-room:1.1.3
```

> http://localhost:8080

## 体验

[聊天室](https://chat-room.fengwenyi.com)

## 预览

![首页](./images/01.png)

![张三聊天截图](./images/02.png)

![李四聊天截图](./images/03.png)

## build

```shell
sh build.sh
```

## docker

```shell
# 拉取镜像
docker pull fengwenyi/erwin-chat-room:1.1.3

# 启动命令（默认预发布）
docker run -d -p 8080:8080 --name erwin-chat-room fengwenyi/erwin-chat-room:1.1.3

# 启动命令（指定正式环境）
docker run -d -e active=prod -p 8080:8080 --name erwin-chat-room fengwenyi/erwin-chat-room:1.1.3

# 查看日志
docker logs -f erwin-chat-room
```

## 链接

- [spring-boot-websocket](https://docs.spring.io/spring-boot/docs/current/reference/html/features.html#features.websockets)
- [spring-framework-websocket](https://docs.spring.io/spring-framework/docs/current/reference/html/web.html#websocket)

## 更新日志

[更新日志](LOG.md)