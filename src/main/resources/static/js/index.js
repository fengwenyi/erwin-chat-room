layui.use(function () {
    var layer = layui.layer
        , form = layui.form
        , laypage = layui.laypage
        , element = layui.element
        , laydate = layui.laydate
        , util = layui.util
        , jQuery = layui.jquery;


    userInit(jQuery);
    userNickname();

    let currentPage = 1;
    const PAGE_SIZE = 50;

    apiGetRoomList(currentPage);

    let layerIndex;

    // 监听点击修改昵按钮
    jQuery('#btn-set-nickname').on('click', function () {
        let nickname = getNickname();
        if (isNotEmpty(nickname)) {
            jQuery('input[name=nickname]').val(nickname);
        }
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
    });

    // 监听创建房间按钮
    jQuery('#btn-create-room').on('click', function () {
        let nickname = getNickname();
        if (isEmpty(nickname)) {
            alertFail(layer, "请先设置昵称")
            return;
        }
        layerIndex = layer.open({
            type: 1,
            title: '创建房间',
            closeBtn: 1, //不显示关闭按钮
            anim: 5,
            shade: [0.5],
            area: ['', ''],
            shadeClose: true, //开启遮罩关闭
            content: jQuery('.box-room')
        });
    });

    // 监听修改用户
    form.on('submit(formUpdateUser)', function (data) {
        userSetNickname(data.field.nickname)
        return false;
    });

    // 监听创建房间
    form.on('submit(formRoom)', function (data) {
        apiCreateRoom(data.field.name, data.field.password)
        return false;
    });

    // 监听房间认证
    form.on('submit(formRoomAuth)', function (data) {
        apiRoomUserAuth(data.field.rid, data.field.password)
        return false;
    });

    function userNickname() {
        let nickname = getNickname();
        if (isNotEmpty(nickname)) {
            jQuery('#nickname').html(nickname);
            jQuery('.icon-update-name').css('display', 'block');
        }
    }

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
                userNickname();
                layer.close(layerIndex);
                alertSuccess(layer, response.msg);
            } else {
                alertFail(layer, response.msg)
            }
        })
    }

    // 创建房间
    function apiCreateRoom(name, password) {
        let room = {};
        room.createUserUid = getUid();
        room.name = name;
        room.password = password;
        ajaxPost(jQuery, layer, "/room/create", JSON.stringify(room), function (response) {
            if (response.success) {
                gotoRoom(response.body.rid)
            } else {
                alertFail(layer, response.msg)
            }
        })
    }

    // 获取房间列表
    function apiGetRoomList(currentPage) {
        let pageRequest = {};
        pageRequest.currentPage = currentPage;
        pageRequest.pageSize = PAGE_SIZE;
        ajaxPost(jQuery, layer, "/room/getPage", JSON.stringify(pageRequest), function (response) {
            if (response.success) {
                // console.log(response.body);
                roomList(response.body.content)
                page(response.body.currentPage, response.body.totalRows)
            } else {
                alertFail(layer, response.msg)
            }
        })
    }

    function roomList(roomList) {
        let htmlContent = '';
        for (let i = 0; i < roomList.length; i++) {
            htmlContent += buildRoomItemHtmlContent(roomList[i]);
        }
        jQuery('#room-list').html(htmlContent)
    }

    function buildRoomItemHtmlContent(room) {
        let htmlContent;

        htmlContent = '<div class="room-item" id="room-' + room.rid + '" onclick="enterRoom(\'' + room.rid + '\')">\n' +
            '                    <div class="back"></div>\n' +
            '                    <div class="content">\n';

        if (room.needPassword) {
            htmlContent += '                        <div class="lock"><i class="layui-icon layui-icon-password"></i></div>\n';
        }

        htmlContent += '                        <div class="name">' + room.name + '</div>\n' +
            '                        <div class="info"><span>' + room.userCount + '</span>人在线</div>\n' +
            '                    </div>\n' +
            '                </div>';


        return htmlContent;
    }

    // 分页
    function page(currentPage, total) {
        //总页数低于页码总数
        laypage.render({
            elem: 'box-page'
            , curr: currentPage
            ,count: total //数据总数
            , limit: PAGE_SIZE
            , jump: function(obj, first){
                //首次不执行
                if(!first){
                    apiGetRoomList(obj.curr)
                }
            }
        });
    }

    // 点击进入房间
    window.enterRoom = function enterRoom(rid) {
        if (isEmpty(getUid())) {
            console.error('用户id为空');
            alertFail(layer, '请关闭浏览器，重新进入');
            return;
        }
        if (isEmpty(getNickname())) {
            alertFail(layer, '请先设置昵称');
            return;
        }
        ajaxGet(jQuery, layer, "/room/" + rid, function (response) {
            if (response.success) {
                if (judgeNeedPassword(response.body.needPassword, response.body.createUserUid)) {
                    // 需要密码
                    //layer.msg("需要密码")
                    // 如果token存在，则直接进房间
                    let token = getRoomUserAuthToken(rid);
                    if (isNotEmpty(token)) {
                        gotoRoom(rid);
                    } else {
                        roomAuthAlert(rid);
                    }
                } else {
                    // 不需要密码
                    //layer.msg("不需要密码");
                    gotoRoom(rid);
                }
            } else {
                alertFail(layer, response.msg);
            }
        });
    }

    // 判断是否需要密码
    function judgeNeedPassword(needPassword, createUserUid) {
        // 房间不需要密码
        if (needPassword === null || !needPassword) {
            return false;
        }
        // 房间需要密码
        // 本地用户id为空
        if (isEmpty(getUid())) {
            return true;
        }
        // 不是创建者
        return getUid() !== createUserUid;
    }



    // 房间认证弹窗
    function roomAuthAlert(rid) {
        jQuery('input[name=rid]').val(rid);
        layerIndex = layer.open({
            type: 1,
            title: '房间认证',
            closeBtn: 1, //不显示关闭按钮
            anim: 5,
            shade: [0.5],
            area: ['', ''],
            shadeClose: true, //开启遮罩关闭
            content: jQuery('.box-room-auth')
        });
    }

    // api房间用户认证
    function apiRoomUserAuth(rid, password) {
        let data = {};
        data.rid = rid;
        data.uid = getUid();
        data.password = password;
        ajaxPost(jQuery, layer, '/auth/room-user', JSON.stringify(data), function (response) {
            if (response.success) {
                setRoomUserAuthToken(rid, response.body.token);
                gotoRoom(rid);
            } else {
                alertFail(layer, response.msg);
            }
        });
    }
});