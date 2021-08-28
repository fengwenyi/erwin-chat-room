function setUid(uid) {
    localStorage.setItem(KEY_UID, uid)
}

function getUid() {
    return localStorage.getItem(KEY_UID)
}

// 保存昵称
function setNickname(nickname) {
    localStorage.setItem(KEY_NICKNAME, nickname)
}

// 获取昵称
function getNickname() {
    return localStorage.getItem(KEY_NICKNAME)
}

// 保存头像背景颜色
function setAvatarBgColor(color) {
    localStorage.setItem(KEY_AVATAR_BG_COLOR, color)
}

// 获取头像背景颜色
function getAvatarBgColor() {
    return localStorage.getItem(KEY_AVATAR_BG_COLOR)
}

// 保存房间用户认证token
function setRoomUserAuthToken(rid, token) {
    localStorage.setItem(KEY_ROOM_USER_AUTH_TOKEN + rid, token)
}

// 获取房间用户认证token
function getRoomUserAuthToken(rid) {
    return localStorage.getItem(KEY_ROOM_USER_AUTH_TOKEN + rid)
}

// 删除房间用户认证token
function deleteRoomUserAuthToken(rid) {
    return localStorage.removeItem(KEY_ROOM_USER_AUTH_TOKEN + rid)
}