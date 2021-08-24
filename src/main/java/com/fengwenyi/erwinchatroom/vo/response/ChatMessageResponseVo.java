package com.fengwenyi.erwinchatroom.vo.response;

import com.fengwenyi.erwinchatroom.enums.MessageTypeEnum;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

/**
 * @author <a href="https://www.fengwenyi.com">Erwin Feng</a>
 * @since 2021-08-24
 */
//@Data
//@Accessors(chain = true)
@Setter
@Getter
public class ChatMessageResponseVo extends MessageResponseVo {

    private UserResponseVo sender;

    // 是否是本人
    private Boolean self;

    public ChatMessageResponseVo() {
        super.setMessageType(MessageTypeEnum.CHAT.getCode());
    }


}
