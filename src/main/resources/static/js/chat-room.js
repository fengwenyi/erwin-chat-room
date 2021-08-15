layui.use(function() {
    let layer = layui.layer
        ,form = layui.form
        ,laypage = layui.laypage
        ,element = layui.element
        ,laydate = layui.laydate
        ,util = layui.util
        ,jQuery = layui.jquery;

    let host = window.location.host;

    let uid = getUid();

    let headImgNo = 0;
    let lastReceiveMsgTimestamp = 0;
    let socket;

    if(typeof(WebSocket) === "undefined") {
        console.error("您的浏览器不支持WebSocket");
    }else {
        //console.log("您的浏览器支持WebSocket");

        //实现化WebSocket对象，指定要连接的服务器地址与端口  建立连接
        socket = new WebSocket("wss://" + host + "/chat-room/" + uid);
        //打开事件
        socket.onopen = function () {
        };
        socket.onmessage = function(messageEvent) {
            //console.log(messageEvent.data)
            let result = JSON.parse(messageEvent.data);
            if (result.success) {
                let response = result.body;
                let msgType = response.msgType;
                // console.log(response)
                switch (msgType) {
                    case 0:
                        handleConnMsg(response);
                        break;
                    case 1:
                        handleBroadcastMsg(response);
                        break;
                    case 2:
                        handleChatMsg(response);
                        break;
                    case 3:
                        handleOnlineUserListMsg(response);
                        break;
                    case 4:
                        handleUserUpLineMsg(response);
                        break;
                }
            } else {
                layer.alert(
                    result.msg, {icon: 5},
                    function () {
                        window.location.href = "/";
                    });
            }
        };
        socket.onclose = function() {
        };
        socket.onerror = function() {
        }
        /*$(window).unload(function(){
            socket.close();
        });*/
    }



    //监听提交
    form.on('submit(formSend)', function(data){

        let message = data.field.message

        socket.send(message);

        jQuery('.layui-input').val('')

        return false;
    });

    function getUid() {
        let url = document.location.toString();
        let start = url.lastIndexOf("/")
        return url.substring(start + 1, url.length)
    }

    function color(){
        let colorAngle = Math.floor(Math.random()*360);
        return 'hsla('+ colorAngle +',100%,50%,1)';
    }

    function handleConnMsg(response) {
        let message = response.message;
        jQuery('#message-conn').html(message);
        jQuery('.conn-container').css('display', 'flex')
        setTimeout(function (){
            jQuery('.conn-container').css('display', 'none')
        }, 3000);
    }
    function handleBroadcastMsg(response) {
        let message = response.message;
        jQuery('.broadcast-container').html(message);
        jQuery('#message-broadcast').css('display', 'flex')
    }
    function handleChatMsg(response) {

        headImgNo++;

        let fromType = response.fromType;
        let sender = response.sender;
        let nickname = sender.nickname;
        let headerText = nickname.substring(0, 1)
        let receiver = response.receiver;
        let message = response.message;
        let timestamp = response.timestamp;
        let timeStr = response.timeStr;
        let self = response.self;

        let htmlContent;

        // 如果大于 1 分钟，则显示
        if (timestamp - lastReceiveMsgTimestamp > 60) {
            htmlContent = buildTimeHtmlContent(timeStr);
        }

        lastReceiveMsgTimestamp = timestamp;

        if (fromType === 1) {
            if (self) {
                // 右边
                htmlContent += buildSelfMessageHtmlContent(headImgNo, headerText, message);

            } else {
                // 左边
                htmlContent += buildOtherMessageHtmlContent(headImgNo, headerText, nickname, message);
            }
        }
        jQuery('#chats-container').append(htmlContent);
        jQuery('#head-' + headImgNo).css('background-color', color())

        let chatDiv = document.getElementById('chats-container')
        chatDiv.scrollTop = chatDiv.scrollHeight;
    }

    function handleOnlineUserListMsg(response) {
        let onlineUserList = response.onlineUserList;
        let htmlContent = '';
        for (let i = 0; i < onlineUserList.length; i++) {
            let user = onlineUserList[i]
            htmlContent += buildOnlineUserHtmlContent(user)
        }
        jQuery('#users-container').html(htmlContent);
    }

    function handleUserUpLineMsg(response) {
        let userUpLine = response.userUpLine;
        let htmlContent = buildOnlineUserHtmlContent(userUpLine)
        jQuery('#users-container').append(htmlContent);
    }

    function buildSelfMessageHtmlContent(headImgNo, headText, message) {
        return '<li class="chat-container right-chat-container">\n' +
            '                    <div class="right">\n' +
            '                        <div class="user-header">\n' +
            '                            <div class="img" id="head-'+ headImgNo + '">' + headText + '</div>\n' +
            '                        </div>\n' +
            '                    </div>\n' +
            '                    <div class="left">\n' +
            '                        <div class="user-message">\n' +
            '                            <div class="content">' + message + '</div>\n' +
            '                            <div class="bubble"></div>\n' +
            '                        </div>\n' +
            '                    </div>\n' +
            '                </li>';
    }

    function buildOtherMessageHtmlContent(headImgNo, headText, nickname, message) {
        return '<li class="chat-container left-chat-container">\n' +
            '                <div class="left">\n' +
            '                    <div class="user-header">\n' +
            '                        <div class="img" id="head-'+ headImgNo + '">' + headText + '</div>\n' +
            '                    </div>\n' +
            '                </div>\n' +
            '                <div class="right">\n' +
            '                    <div class="user-nickname">' + nickname + '</div>\n' +
            '                    <div class="user-message">\n' +
            '                        <div class="content">' + message + '</div>\n' +
            '                        <div class="bubble"></div>\n' +
            '                    </div>\n' +
            '                </div>\n' +
            '            </li>';
    }

    function buildTimeHtmlContent(timeStr) {
        return '<li class="chat-container center-chat-container">\n' +
            '                <div class="time">' + timeStr + '</div>\n' +
            '            </li>';
    }

    function buildOnlineUserHtmlContent(user) {
        return '<li class="user-container">\n' +
            '                <div class="nickname">' + user.nickname + '</div>\n' +
            '                <div class="time">' + user.timeStr + '</div>\n' +
            '            </li>';
    }
});


