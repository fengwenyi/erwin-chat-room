package com.fengwenyi.erwinchatroom.vo.response;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author <a href="https://www.fengwenyi.com">Erwin Feng</a>
 * @since 2021-08-12
 */
@Data
@Accessors(chain = true)
public class ResponseVo {

    private Integer msgType;

    private Integer fromType;

    private UserResponseVo sender;

    private UserResponseVo receiver;

    private String message;

    // 秒
    private Long timestamp;

    private String timeStr;

    // 是否是本人
    private Boolean self;
    // 在线用户列表
    private List<UserResponseVo> onlineUserList;
    // 用户上线
    private UserResponseVo userUpLine;

}
