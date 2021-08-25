package com.fengwenyi.erwinchatroom.vo.response;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @author <a href="https://www.fengwenyi.com">Erwin Feng</a>
 * @since 2021-08-21
 */
@Data
@Accessors(chain = true)
public class RoomResponseVo {

    private String rid;

    private String name;

    private Boolean needPassword;

    private String createUserUid;

    private String createUserNickname;

    private String createTimeString;

    private Integer userCount;
}
