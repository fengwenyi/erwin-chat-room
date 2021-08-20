layui.use(function () {
    var layer = layui.layer
        , form = layui.form
        , laypage = layui.laypage
        , element = layui.element
        , laydate = layui.laydate
        , util = layui.util
        , jQuery = layui.jquery;


    userInit();
    userNickname();

    // 监听点击修改昵按钮
    jQuery('#btn-set-nickname').on('click', function () {
        let nickname = getNickname();
        if (isNotEmpty(nickname)) {
            jQuery('input[name=nickname]').val(nickname);
        }
        layer.open({
            type: 1,
            title: '设置',
            closeBtn: 1, //不显示关闭按钮
            anim: 5,
            shade: [0.5],
            area: ['', ''],
            shadeClose: true, //开启遮罩关闭
            content: jQuery('.box-update-user')
        });
    });

    // 监听创建房间按钮
    jQuery('#btn-create-room').on('click', function () {
        let nickname = getNickname();
        if (isEmpty(nickname)) {
            alertFail(layer, "请先设置昵称")
        }
    });

    form.on('submit(formUpdateUser)', function (data) {
        userSetNickname(data.field.nickname)
        return false;
    });

    //监听提交
    form.on('submit(formEnter)', function (data) {
        //console.log(JSON.stringify(data.field));
        let layerIndex;
        jQuery.ajax({
            url: "/business/register",
            type: 'POST',
            // 请求的媒体类型
            contentType: "application/json;charset=UTF-8",
            data: JSON.stringify(data.field),
            beforeSend: function () {
                layerIndex = layer.load(0, {shade: 0.1});
            },
            success: function (response) {
                if (response.success) {
                    //layer.alert(response.msg, { icon: 6 });
                    let uid = response.body.uid
                    window.location.href = '/chat-room/' + uid
                } else {
                    layer.alert(response.msg, {icon: 5});//失败的表情
                }
            },
            complete: function () {
                layer.close(layerIndex);
            },
        });
        return false;
    });

    let sessionId;

    function userInit2() {
        jQuery.ajax({
            url: "/auth/userInit",
            type: 'POST',
            // 请求的媒体类型
            contentType: "application/json;charset=UTF-8",
            //data: JSON.stringify(data.field),
            beforeSend: function () {
                //layerIndex = layer.load(0, { shade: 0.1 });
            },
            success: function (response) {
                if (response.success) {

                    sessionId = response.body;
                    console.log("sessionId=" + sessionId)

                    connRoom()

                    //layer.alert(response.msg, { icon: 6 });
                    //let uid = response.body.uid
                    //window.location.href = '/chat-room/' + uid
                } else {
                    layer.alert(response.msg, {icon: 5});//失败的表情
                }
            },
            complete: function () {
                layer.close(layerIndex);
            },
        });
        return false;
    }

    function connRoom() {
        let socket = new WebSocket("ws://localhost:8080/room?" + sessionId);
        socket.onopen = function () {
            console.log('open');
            socket.send('test');
        };

        socket.onmessage = function (e) {
            console.log('message', e.data);
            socket.close();
        };

        socket.onclose = function () {
            console.log('close');
        };
    }


    function connChat() {
        let sock = new SockJS("http://localhost:8080/room?sessionId=" + sessionId)
        sock.onopen = function () {
            console.log('open');
            sock.send('test');
        };

        sock.onmessage = function (e) {
            console.log('message', e.data);
            sock.close();
        };

        sock.onclose = function () {
            console.log('close');
        };
    }

    function userInit() {
        let uid = getUid();
        if (isEmpty(uid)) {
            uid = ''
        }
        let user = { uid : uid};
        ajaxPost(jQuery, layer, "/user/init", JSON.stringify(user), function (response) {
            if (response.success) {
                if (isNotEmpty(response.body.uid)) {
                    setUid(response.body.uid)
                }
            } else {
                console.error('user init error : ' + response.msg)
            }
        })
    }

    function userNickname() {
        let nickname = getNickname();
        if (isNotEmpty(nickname)) {
            jQuery('#nickname').html(nickname);
            jQuery('.icon-update-name').css('display', 'block');
        }
    }

    function userSetNickname(nickname) {
        let uid = getUid();
        let user = {};
        user.uid = uid;
        user.nickname = nickname;
        ajaxPost(jQuery, layer, "/user/update", JSON.stringify(user), function (response) {
            if (response.success) {
                setNickname(nickname)
                userNickname();
                alertSuccess(layer, response.msg)
            } else {
                alertFail(layer, response.msg)
            }
        })
    }
});