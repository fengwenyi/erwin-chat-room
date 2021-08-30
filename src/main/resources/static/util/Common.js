/**
 * 用户初始化
 */
function userInit(jQuery) {
    let user = {};
    user.uid = getUid();
    user.nickname = getNickname();
    user.avatarBgColor = getAvatarBgColor();
    //console.log('用户初始化-请求参数={}' + JSON.stringify(user))
    ajaxPost(jQuery, layer, "/user/init", JSON.stringify(user), function (response) {
        //console.log('用户初始化-响应参数={}', response)
        if (response.success) {
            if (isNotEmpty(response.body.uid)) {
                setUid(response.body.uid)
            }
        } else {
            console.error('user init error : ' + response.msg)
        }
    });
}

// 去房间
function gotoRoom(rid) {
    let uid = getUid();
    let token = getRoomUserAuthToken(rid);
    let url = '/chat/' + rid + '/' + uid + '?ct=' + new Date().getTime();
    if (isNotEmpty(token)) {
        url += '&token=' + token;
    }
    window.location.href = url;
}