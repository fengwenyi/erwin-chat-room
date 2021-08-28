package com.fengwenyi.erwinchatroom.vo.request;

import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * @author <a href="https://www.fengwenyi.com">Erwin Feng</a>
 * @since 2021-08-14
 */
@Data
@Accessors(chain = true)
public class UserRequestVo {

    @NotBlank(message = "用户ID不能为空")
    private String uid;

    @NotBlank(message = "用户昵称不能为空")
    private String nickname;

    @NotBlank(message = "用户头像背景颜色不能为空")
    private String avatarBgColor;

}
