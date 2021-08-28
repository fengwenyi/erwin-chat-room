package com.fengwenyi.erwinchatroom.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author <a href="https://www.fengwenyi.com">Erwin Feng</a>
 * @since 2021-08-18
 */
@Entity
@Table(name = "t_user")
@Data
@Accessors(chain = true)
public class UserEntity {

    @Id
    private String uid;

    private String nickname;

    // 头像背景颜色
    private String avatarBgColor;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private LocalDateTime loginTime;

    private Boolean register;
}
