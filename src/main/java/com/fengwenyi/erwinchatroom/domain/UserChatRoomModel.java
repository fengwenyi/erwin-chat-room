package com.fengwenyi.erwinchatroom.domain;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.websocket.Session;
import java.time.LocalDateTime;

/**
 * @author <a href="https://www.fengwenyi.com">Erwin Feng</a>
 * @since 2021-08-15
 */
@Data
@Accessors(chain = true)
public class UserChatRoomModel {

    private String uid;

    private String nickname;

    private Session session;

    private LocalDateTime time;
}
