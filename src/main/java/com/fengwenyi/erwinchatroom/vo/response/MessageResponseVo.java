package com.fengwenyi.erwinchatroom.vo.response;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

/**
 * @author <a href="https://www.fengwenyi.com">Erwin Feng</a>
 * @since 2021-08-24
 */
@Getter
@Setter
public class MessageResponseVo {

    private Integer messageType;

    private ContentResponseVo message;

    // 秒
    private Long timestamp;

    private String timeStr;

    public MessageResponseVo() {
    }
}
