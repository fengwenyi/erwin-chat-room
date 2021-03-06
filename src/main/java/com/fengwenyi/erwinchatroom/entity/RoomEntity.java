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
@Table(name = "t_room")
@Data
@Accessors(chain = true)
public class RoomEntity {

    @Id
    private String rid;

    private String name;

    private Boolean needPassword;

    private String password;

    private LocalDateTime createTime;

    private String createUserUid;

    private Integer userCount;

}
