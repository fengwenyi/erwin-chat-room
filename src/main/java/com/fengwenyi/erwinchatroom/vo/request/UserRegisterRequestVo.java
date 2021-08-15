package com.fengwenyi.erwinchatroom.vo.request;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

/**
 * @author <a href="https://www.fengwenyi.com">Erwin Feng</a>
 * @since 2021-08-14
 */
@Data
@Accessors(chain = true)
public class UserRegisterRequestVo {

    @NotBlank(message = "用户昵称不能为空")
    private String nickname;

}
