package com.fengwenyi.erwinchatroom.enums;

import lombok.Getter;

import java.util.Objects;

/**
 * @author <a href="https://www.fengwenyi.com">Erwin Feng</a>
 * @since 2021-08-24
 */
@Getter
public enum ChatContentTypeEnum {

    TEXT(1, "文本")
    , image(2, "图片")
    , AUDIO(3, "音频")
    , VIDEO(4, "视频")

    ;

    private final Integer code;

    private final String desc;

    ChatContentTypeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static ChatContentTypeEnum getType(Integer code) {
        if (Objects.isNull(code)) {
            return null;
        }
        for (ChatContentTypeEnum typeEnum : ChatContentTypeEnum.values()) {
            if (Objects.equals(typeEnum.code, code)) {
                return typeEnum;
            }
        }
        return null;
    }
}
