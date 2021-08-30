package com.fengwenyi.erwinchatroom.vo.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 房间邀请校验
 * @author <a href="https://www.fengwenyi.com">Erwin Feng</a>
 * @since 2021-08-29
 */
@Data
public class RoomInviteAuthRequestVo {

    @NotBlank(message = "房间ID不能为空")
    private String rid;

    @NotBlank(message = "用户ID不能为空")
    private String uid;

    @NotBlank(message = "邀请者ID不能为空")
    private String inviteUid;

}
