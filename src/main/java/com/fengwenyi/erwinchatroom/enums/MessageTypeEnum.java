package com.fengwenyi.erwinchatroom.enums;

import lombok.Getter;

/**
 * @author <a href="https://www.fengwenyi.com">Erwin Feng</a>
 * @since 2021-08-24
 */
@Getter
public enum MessageTypeEnum {

    TEXT(1, "文本")

    ;

    private final Integer code;

    private final String desc;

    MessageTypeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
