package com.fengwenyi.erwinchatroom.enums;

import lombok.Getter;

/**
 * @author <a href="https://www.fengwenyi.com">Erwin Feng</a>
 * @since 2021-08-15
 */
@Getter
public enum UserStatusEnum {

    OFFLINE(0, "离线")
    , ONLINE(1, "在线")

    ;

    private final int code;

    private final String desc;

    UserStatusEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
