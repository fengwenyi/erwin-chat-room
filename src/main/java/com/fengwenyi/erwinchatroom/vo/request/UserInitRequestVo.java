package com.fengwenyi.erwinchatroom.vo.request;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author <a href="https://www.fengwenyi.com">Erwin Feng</a>
 * @since 2021-08-20
 */
@Data
@Accessors(chain = true)
public class UserInitRequestVo {

    private String uid;

    private String nickname;

    private String avatarBgColor;

}
