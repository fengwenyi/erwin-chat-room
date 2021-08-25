package com.fengwenyi.erwinchatroom.vo.response;

import com.fengwenyi.erwinchatroom.enums.MessageTypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author <a href="https://www.fengwenyi.com">Erwin Feng</a>
 * @since 2021-08-24
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class ChatMessageResponseVo extends MessageResponseVo {

    private UserResponseVo sender;

    public ChatMessageResponseVo() {
        super.setMessageType(MessageTypeEnum.CHAT.getCode());
    }


}
