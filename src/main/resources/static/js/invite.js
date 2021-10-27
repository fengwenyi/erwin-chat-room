layui.use(function () {
    var layer = layui.layer
        , form = layui.form
        , laypage = layui.laypage
        , element = layui.element
        , laydate = layui.laydate
        , util = layui.util
        , jQuery = layui.jquery;

    let layerIndex;

    userInit(jQuery);

    // 拒绝
    function handleRefuse() {
        layer.msg('感谢！')
    }

    // 加入
    function handleJoinIn() {
        /**
         * 有没有uid
         * 有：
         * 没有：初始化
         *
         * 有没有昵称
         * 有：
         * 没有：设置昵称
         */
        let nickname = getNickname();
        if (isEmpty(nickname)) {
            // 弹窗
            layerIndex = layer.open({
                type: 1,
                title: '设置昵称',
                closeBtn: 1, //不显示关闭按钮
                anim: 5,
                shade: [0.5],
                area: ['', ''],
                shadeClose: true, //开启遮罩关闭
                content: jQuery('.box-update-user')
            });
        } else {
            gotoRoom(rid);
        }
    }

    // 监听修改用户
    form.on('submit(formUpdateUser)', function (data) {
        userSetNickname(data.field.nickname)
        return false;
    });

    function userSetNickname(nickname) {
        let uid = getUid();
        let avatarBgColor = getAvatarBgColor();
        if (isEmpty(avatarBgColor)) {
            avatarBgColor = recommendColor();
        }
        let user = {};
        user.uid = uid;
        user.nickname = nickname;
        user.avatarBgColor = avatarBgColor;
        ajaxPost(jQuery, layer, "/user/update", JSON.stringify(user), function (response) {
            if (response.success) {
                setNickname(nickname);
                setAvatarBgColor(avatarBgColor);
                layer.close(layerIndex);
                gotoRoom(rid);
            } else {
                alertFail(layer, response.message)
            }
        })
    }

    window.btnClickJoinInRoom = function () {
        handleJoinIn();
    }

    window.btnClickRefuse = function () {
        handleRefuse();
    }


});