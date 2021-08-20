function setUid(uid) {
    localStorage.setItem(KEY_UID, uid)
}

function getUid() {
    return localStorage.getItem(KEY_UID)
}

function alertFail(layer, msg) {
    layer.alert(msg,{ icon: 5 });//失败的表情
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