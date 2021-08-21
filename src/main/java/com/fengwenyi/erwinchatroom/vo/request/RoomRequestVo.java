package com.fengwenyi.erwinchatroom.vo.request;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

/**
 * @author <a href="https://www.fengwenyi.com">Erwin Feng</a>
 * @since 2021-08-21
 */
@Data
@Accessors(chain = true)
public class RoomRequestVo {

    @NotBlank(message = "房间名称不能为空")
    private String name;

    private String password;

    @NotBlank(message = "创建者ID不能为空")
    private String createUserUid;

}
