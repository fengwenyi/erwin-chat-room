package com.fengwenyi.erwinchatroom.enums;

import lombok.Getter;

/**
 * @author <a href="https://www.fengwenyi.com">Erwin Feng</a>
 * @since 2021-08-24
 */
@Getter
public enum MessageTypeEnum {

    CHAT(1, "聊天")
    , BROADCAST(9, "广播")

    ;

    private final Integer code;

    private final String desc;

    MessageTypeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
