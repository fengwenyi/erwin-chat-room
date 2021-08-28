layui.use(function() {
    let layer = layui.layer
        ,form = layui.form
        ,laypage = layui.laypage
        ,element = layui.element
        ,laydate = layui.laydate
        ,util = layui.util
        ,jQuery = layui.jquery;

    let host = window.location.host;

    let headImgNo = 0;
    let lastReceiveMsgTimestamp = 0;
    let socket;
    let stompClient = {};

    let needClose = false;

    if(typeof(WebSocket) === "undefined") {
        console.error("您的浏览器不支持WebSocket");
    } else {
        if (!error) {
            connect();
            needClose = true;

            // 5s 自动重连
            // 有问题
            // setInterval(function () {
            //     connect();
            // }, 5000)
        }
    }

    function connect() {
        let sock = new SockJS("http://localhost:8080/ws")
        stompClient = Stomp.over(sock);//使用STMOP子协议的WebSocket客户端
        stompClient.debug = false;
        let header = {};
        header.rid = rid;
        header.uid = getUid();
        stompClient.connect(header, function(frame){//连接WebSocket服务端

            apiGetRoomUserCount();
            apiGetRoomUsers();

            // console.log('Connected:' + frame);
            //通过stompClient.subscribe订阅/topic/getResponse 目标(destination)发送的消息
            stompClient.subscribe('/topic/getResponse',function(response){
                showResponse(JSON.parse(response.body));
            });

            // 接收房间聊天消息
            receiverRoomChatMessage();
        }, function (err) {});
    }

    //监听提交
    form.on('submit(formSend)', function(data){

        let message = data.field.message;

        sendChatMessage(1, message)

        jQuery('.layui-input').val('')

        return false;
    });

    //关闭双通道
    function disconnect(){
        if(stompClient != null) {
            let header = {};
            header.rid = rid;
            header.uid = getUid();
            stompClient.disconnect(header);
        }
        console.log("Disconnected");
    }
    function showResponse(message){
        console.log(message)
    }


    //强制关闭浏览器  调用websocket.close（）,进行正常关闭
    /*window.onbeforeunload = function(e) {
        //disconnect()
        //return "退出吗?";
        e.preventDefault();
        e.returnValue = "注意！！\n您即将离开页面！离开后可能会导致数据丢失\n\n您确定要离开吗？";
        //return e;
    }*/

    if (needClose) {
        window.onbeforeunload = function (e) {
            e = e || window.event;

            disconnect();

            let tip = "注意！！\\n您即将离开页面！离开后将端开服务器链接，可能会导致数据丢失\\n\\n您确定要离开吗？";

            // 兼容IE8和Firefox 4之前的版本
            if (e) {
                e.returnValue = tip;
            }



            // Chrome, Safari, Firefox 4+, Opera 12+ , IE 9+
            return tip;
        };
    }



    // 接收房间聊天消息
    function receiverRoomChatMessage() {
        stompClient.subscribe('/room/' + rid, function(response) {
            console.info(response)
            let result = JSON.parse(response.body);
            if (result.success) {
                let messageVo = result.body;
                let messageType = messageVo.messageType;
                if (messageType === 9) {
                    handleRoomBroadcastMessage(messageVo)
                } else if (messageType === 1) {
                    handleRoomChatMessage(messageVo);
                } else {
                    console.error('未知消息类型：' + messageType)
                }
            } else {
                console.error(result.msg)
            }
        });
    }


    // 处理房间聊天消息
    function handleRoomChatMessage(chatMessageVo) {
        let timestamp = chatMessageVo.timestamp;
        let timeStr = chatMessageVo.timeStr;
        let sender = chatMessageVo.sender;

        let message = chatMessageVo.message;

        let htmlContent = handleRoomChatMessageTimeTip(timestamp, timeStr);

        if (judgeMessageFromSelf(sender)) {
            // 自己发的消息
            htmlContent += buildHtmlContentSelfChatMessage(sender, message)
        } else {
            // 其他人发的消息
            htmlContent += buildHtmlContentOtherChatMessage(sender, message)
        }

        jQuery('#chat-container').append(htmlContent);
        //jQuery('#head-' + headImgNo).css('background-color', color())

        let chatDiv = document.getElementById('center-container')
        chatDiv.scrollTop = chatDiv.scrollHeight;
    }

    // 判断是不是本人发的消息
    function judgeMessageFromSelf(sender) {
        if (sender === null) {
            return false;
        }
        let uid = sender.uid;
        if (isEmpty(uid)) {
            return false;
        }
        return uid === getUid();
    }

    // 处理房间广播消息
    function handleRoomBroadcastMessage(broadcastMessageVo) {
        let message = broadcastMessageVo.message;

        let contentType = message.contentType;
        let content = message.content;

        if (contentType === 301) {
            layer.msg(content);

            // 房间人数
            apiGetRoomUserCount();

            // 房间用户列表
            apiGetRoomUsers();
        }
    }

    function handleGetRoomUserCount(userCount) {
        jQuery('#room-user-count').html(userCount);
    }

    function handleRoomChatMessageTimeTip(timestamp, timeStr) {
        let htmlContent;
        // 如果大于 1 分钟，则显示
        if (timestamp - lastReceiveMsgTimestamp > 60) {
            htmlContent = buildHtmlContentChatTime(timeStr);
        }

        lastReceiveMsgTimestamp = timestamp;

        return htmlContent;
    }

    // 构造 htmlContent 其他人聊天内容
    function buildHtmlContentOtherChatMessage(sender, message) {
        let nickname = sender.nickname;
        let contentType = message.contentType;
        let content = message.content;
        return '<div class="box-chat box-chat-other">\n' +
            '                    <div class="left">\n' +
            '                        <div class="user-header">\n' +
            '                            <div class="img" style="background-color: '+ sender.avatarBgColor +';">' + buildUserHeadText(sender.nickname) + '</div>\n' +
            '                        </div>\n' +
            '                    </div>\n' +
            '                    <div class="right">\n' +
            '                        <div class="user-nickname">' + nickname + '</div>\n' +
            '                        <div class="user-message">\n' +
            '                            <div class="content">' + content + '</div>\n' +
            '                            <div class="bubble"></div>\n' +
            '                        </div>\n' +
            '                    </div>\n' +
            '                </div>';
    }

    //
    function buildHtmlContentSelfChatMessage(sender, message) {
        let contentType = message.contentType;
        let content = message.content;
        return '<div class="box-chat box-chat-self">\n' +
            '                    <div class="right">\n' +
            '                        <div class="user-header">\n' +
            '                            <div class="img" style="background-color: '+ sender.avatarBgColor +';">' + buildUserHeadText(sender.nickname) + '</div>\n' +
            '                        </div>\n' +
            '                    </div>\n' +
            '                    <div class="left">\n' +
            '                        <div class="user-message">\n' +
            '                            <div class="content">' + content + '</div>\n' +
            '                            <div class="bubble"></div>\n' +
            '                        </div>\n' +
            '                    </div>\n' +
            '                </div>';
    }

    // 构造 htmlContent 时间提示
    function buildHtmlContentChatTime(timeStr) {
        return '<div class="box-chat box-chat-time">\n' +
            '                    <div class="time">' + timeStr + '</div>\n' +
            '                </div>';
    }



    /**
     * 发送聊天消息
     * @param format 格式：1-文本
     * @param content 内容
     */
    function sendChatMessage(format, content) {
        let data = {};
        data.rid = rid;
        data.uid = getUid();
        let message = {};
        message.contentType = format;
        message.content = content;
        data.message = message;
        stompClient.send("/app/chat/room", {}, JSON.stringify(data));
    }

    // 获取房间用户数量
    function apiGetRoomUserCount() {
        ajaxGet(jQuery, layer, '/room/' + rid + '/user-count', function (response) {
            console.log(response)
            if (response.success) {
                handleGetRoomUserCount(response.body)
            }
        });
    }

    // 获取房间用户列表
    function apiGetRoomUsers() {
        ajaxGet(jQuery, layer, '/room/' + rid + '/users', function (response) {
            console.log(response)
            if (response.success) {
                handleGetRoomUsers(response.body)
            }
        });
    }

    // 处理获取的房间用户列表
    function handleGetRoomUsers(users) {
        let htmlContent = '';
        for (let i = 0; i < users.length; i++) {
            htmlContent += buildHtmlContentUser(users[i])
        }
        jQuery("#user-container").html(htmlContent)
    }

    // 拼接房间用户
    function buildHtmlContentUser(user) {
        return '<div class="box-user" id="' + user.uid + '">\n' +
            '                <div class="nickname">' + user.nickname + '</div>\n' +
            '                <div class="up-time">' + user.timeStr + '</div>\n' +
            '            </div>';
    }

    /**
     * 获取用户头像文本
     * @param nickname
     * @returns {string}
     */
    function buildUserHeadText(nickname) {
        return nickname.substring(0, 1);
    }

});

// bindCloseBrowser()

//浏览器关闭或刷新事件
// function bindCloseBrowser() {
//     var a = "注意！！\n您即将离开页面！离开后可能会导致数据丢失\n\n您确定要离开吗？";
//     window.onbeforeunload = function (b) {
//         b = b || window.event;
//         b.returnValue = a;
//         return a
//     }
// }