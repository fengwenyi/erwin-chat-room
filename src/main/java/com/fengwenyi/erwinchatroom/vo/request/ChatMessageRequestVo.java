package com.fengwenyi.erwinchatroom.vo.request;

import lombok.Data;

/**
 * @author <a href="https://www.fengwenyi.com">Erwin Feng</a>
 * @since 2021-08-24
 */
@Data
public class ChatMessageRequestVo extends MessageRequestVo {

    // 房间ID
    private String rid;

    private String uid;


}
