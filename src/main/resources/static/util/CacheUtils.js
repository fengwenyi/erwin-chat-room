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
function setAvatarBgColor(avatarBgColor) {
    localStorage.setItem(KEY_AVATAR_BG_COLOR, avatarBgColor)
}

// 获取头像背景颜色
function getAvatarBgColor() {
    return localStorage.getItem(KEY_AVATAR_BG_COLOR)
}