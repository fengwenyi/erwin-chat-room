package com.fengwenyi.erwinchatroom.vo.response;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author <a href="https://www.fengwenyi.com">Erwin Feng</a>
 * @since 2021-09-02
 */
@Data
@Accessors(chain = true)
public class RoomInviteResponseVo {

    private String inviteUrl;

    private String roomName;

    private String userNickname;

}
