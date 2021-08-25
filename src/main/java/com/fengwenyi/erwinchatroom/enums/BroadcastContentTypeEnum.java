package com.fengwenyi.erwinchatroom.enums;

import lombok.Getter;

/**
 * 广播内容类型
 * @author <a href="https://www.fengwenyi.com">Erwin Feng</a>
 * @since 2021-08-24
 */
@Getter
public enum BroadcastContentTypeEnum {

    SYSTEM(100, "系统广播")
    , ROOM(200, "房间广播")

    , TIP_USER_ENTER_ROOM(301, "提示广播：用户进入房间")
    , TIP_USER_LEAVE_ROOM(302, "提示广播：用户进入房间")

    ;

    private final Integer code;

    private final String desc;

    BroadcastContentTypeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
