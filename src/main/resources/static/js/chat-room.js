let socket;
if(typeof(WebSocket) === "undefined") {
    console.log("您的浏览器不支持WebSocket");
}else {
    console.log("您的浏览器支持WebSocket");

    //实现化WebSocket对象，指定要连接的服务器地址与端口  建立连接
    socket = new WebSocket("ws://localhost:8080/chat-room");
    //打开事件
    socket.onopen = function () {
        console.log("Socket 已打开");
        //socket.send("这是来自客户端的消息" + location.href + new Date());
    };
    socket.onmessage = function(messageEvent) {
        console.log(messageEvent.data)
    };
}