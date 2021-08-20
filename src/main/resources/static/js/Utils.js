function setUid(uid) {
    localStorage.setItem(KEY_UID, uid)
}

function getUid() {
    return localStorage.getItem(KEY_UID)
}

// 弹窗-失败
function alertFail(layer, msg) {
    layer.alert(msg,{ icon: 5 });//失败的表情
}

// 弹窗-成功
function alertSuccess(layer, msg) {
    layer.alert(msg, { icon: 6 });
}

function isEmpty(x) {
    return x === null
        || x === undefined
        || x.trim() === ''
        || x.trim() === 'null'
        || x.trim() === 'undefined';
}

function isNotEmpty(x) {
    return !isEmpty(x);
}

// 保存昵称
function setNickname(nickname) {
    localStorage.setItem(KEY_NICKNAME, nickname)
}

// 获取昵称
function getNickname() {
    return localStorage.getItem(KEY_NICKNAME)
}