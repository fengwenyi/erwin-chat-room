package com.fengwenyi.erwinchatroom.enums;

import lombok.Getter;

/**
 * 广播内容类型
 * @author <a href="https://www.fengwenyi.com">Erwin Feng</a>
 * @since 2021-08-24
 */
@Getter
public enum BroadcastContentTypeEnum {

    SYSTEM(9, "系统广播")
    , ROOM(1, "房间广播")
    , TIP(2, "提示广播")

    ;

    private final Integer code;

    private final String desc;

    BroadcastContentTypeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
