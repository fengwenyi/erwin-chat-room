package com.fengwenyi.erwinchatroom.vo.response;

import com.fengwenyi.erwinchatroom.enums.MessageTypeEnum;

/**
 * @author <a href="https://www.fengwenyi.com">Erwin Feng</a>
 * @since 2021-08-25
 */
public class BroadcastMessageResponseVo extends MessageResponseVo {

    public BroadcastMessageResponseVo() {
        super.setMessageType(MessageTypeEnum.BROADCAST.getCode());
    }
}
