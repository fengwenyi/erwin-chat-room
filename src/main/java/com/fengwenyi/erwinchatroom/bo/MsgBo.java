package com.fengwenyi.erwinchatroom.bo;

import com.fengwenyi.erwinchatroom.domain.UserChatRoomModel;
import com.fengwenyi.erwinchatroom.enums.FromTypeEnum;
import com.fengwenyi.erwinchatroom.enums.MsgTypeEnum;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author <a href="https://www.fengwenyi.com">Erwin Feng</a>
 * @since 2021-08-15
 */
@Data
@Accessors(chain = true)
public class MsgBo {

    private MsgTypeEnum msgTypeEnum;
    private String message;
    private FromTypeEnum fromTypeEnum;
    private UserChatRoomModel sender;
    private UserChatRoomModel receiver;
    private long timestamp;
    private String timeStr;
    private List<UserChatRoomModel> onlineUserList;
    private UserChatRoomModel userUpLine;

}
