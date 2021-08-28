package com.fengwenyi.erwinchatroom.vo.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 房间用户校验
 * @author <a href="https://www.fengwenyi.com">Erwin Feng</a>
 * @since 2021-08-29
 */
@Data
public class RoomUserAuthRequestVo {

    @NotBlank(message = "房间ID不能为空")
    private String rid;

    @NotBlank(message = "用户D不能为空")
    private String uid;

    @NotBlank(message = "密码不能为空")
    private String password;

}
