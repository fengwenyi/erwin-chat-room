layui.use(function() {
    let layer = layui.layer
        ,form = layui.form
        ,laypage = layui.laypage
        ,element = layui.element
        ,laydate = layui.laydate
        ,util = layui.util
        ,jQuery = layui.jquery;

    let host = window.location.host;

    let lastReceiveMsgTimestamp = 0;
    let stompClient = {};
    let layerIndex;

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

        // let wsUrl = 'http://' + host + '/ws';
        // let wsUrl = '/ws?rid=' + rid + '&uid=' + getUid() + '&ct=' + new Date().getTime();
        let wsUrl = domain + '/portfolio?rid=' + rid + '&uid=' + getUid() + '&ct=' + new Date().getTime();
        let token = getRoomUserAuthToken(rid);
        if (isNotEmpty(token)) {
            wsUrl += '&token=' + token;
        }

        let sock = new SockJS(wsUrl);
        stompClient = Stomp.over(sock);//使用STMOP子协议的WebSocket客户端
        stompClient.debug = false;
        let header = {};
        header.rid = rid;
        header.uid = getUid();
        stompClient.connect(header, function(frame){//连接WebSocket服务端

            apiGetRoomUserCount();
            apiGetRoomUsers();

            // 接收房间聊天消息
            receiverRoomChatMessage();

        }/*, function (err) {

        }*/);
        //stompClient.retry(false)
    }

    function errorCallBack (error) {
        // 连接失败时（服务器响应 ERROR 帧）的回调方法
        document.getElementById("state-info").innerHTML = "连接失败";
        console.log('连接失败【' + error + '】');
        //return false;
        return true;
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
            // console.info(response)
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
            // console.log(response)
            if (response.success) {
                handleGetRoomUserCount(response.body)
            }
        });
    }

    // 获取房间用户列表
    function apiGetRoomUsers() {
        ajaxGet(jQuery, layer, '/room/' + rid + '/users', function (response) {
            // console.log(response)
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


    // api 邀请
    function apiInvite() {
        let url = '/room/' + rid + '/invite?inviteUid=' + getUid();
        ajaxGet(jQuery, layer, url, function (response) {
            if (response.success) {

            } else {
                alertFail(response.msg)
            }
        });
    }

    // 点击邀请按钮
    window.btnClickInvite = function btnClickInvite() {

        handleGenInviteQRCodeImg();

        layerIndex = layer.open({
            type: 1,
            title: '邀请',
            // closeBtn: 1, //不显示关闭按钮
            anim: 5,
            shade: [0.5],
            area: ['', ''],
            shadeClose: true, //开启遮罩关闭
            content: jQuery('.box-invite')
        });
    }

    function handleGenInviteQRCodeImg() {
        apiGetRoomInviteUrl();
    }

    // api 获取邀请邀请url
    function apiGetRoomInviteUrl() {
        let url = '/room/' + rid + '/invite?inviteUid=' + getUid();
        ajaxGet(jQuery, layer, url, function (response) {
            if (response.success) {
                let roomName = response.body.roomName;
                let userNickname = response.body.userNickname;
                let inviteUrl = response.body.inviteUrl;
                //console.log(inviteUrl);
                apiUrlConversionQRCodeBase64(inviteUrl);
                handleSetInviteInfo(roomName, userNickname);
            } else {
                alertFail(response.msg);
            }
        });
    }

    // url 转 二维码 base64
    function apiUrlConversionQRCodeBase64(inviteUrl) {
        let data = {};
        data.url = inviteUrl;
        ajaxPost(jQuery, layer, '/qr-code/generator', JSON.stringify(data), function (response) {
            if (response.success) {
                let imgBase64 = response.body;
                //console.log(imgBase64);
                jQuery('#inviteQRCodeImg').attr('src', 'data:image/png;base64,' + imgBase64);
            } else {
                alertFail(response.msg);
            }
        });
    }

    // 邀请相关信息
    function handleSetInviteInfo(roomName, userNickname) {
        jQuery('#invite-room-name').html(roomName);
        jQuery('#invite-user-nickname').html(userNickname);
    }


    // 点击 下载 邀请图片
    window.btnClickSaveImg = function () {
        saveInviteQRCodeImg();
    };

    function saveInviteQRCodeImg() {
        let fileName = new Date().getTime().toString(16) + Math.ceil(Math.random() * 10000).toString(16);
        html2canvas(document.getElementById("box-invite-img")).then(function(canvas) {
            //canvas转换成url，然后利用a标签的download属性，直接下载
            let a = document.createElement("a");
            a.href = canvas.toDataURL('image/jpeg', 1.0);
            //设置下载文件的名字
            a.download = 'ecr-invite-' + fileName + ".jpg";
            //点击
            a.click();
        });
    }

});
