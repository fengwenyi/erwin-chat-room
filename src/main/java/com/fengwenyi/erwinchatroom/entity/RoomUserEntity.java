package com.fengwenyi.erwinchatroom.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;

/**
 * @author <a href="https://www.fengwenyi.com">Erwin Feng</a>
 * @since 2021-08-18
 */
@Entity
@Table(name = "t_room_user")
@Data
@Accessors(chain = true)
public class RoomUserEntity {

    @Id
    private String sessionId;

    private String rid;

    private String uid;

}
