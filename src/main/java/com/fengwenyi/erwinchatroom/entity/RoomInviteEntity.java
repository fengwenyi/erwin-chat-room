package com.fengwenyi.erwinchatroom.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

/**
 * 房间邀请记录表
 * @author <a href="https://www.fengwenyi.com">Erwin Feng</a>
 * @since 2021-08-18
 */
@Entity
@Table(name = "t_room_invite")
@Data
@Accessors(chain = true)
public class RoomInviteEntity {

    // 邀请ID
    @Id
    private String id;

    // 房间ID
    private String rid;

    // 邀请者ID
    private String inviteUid;

    // 邀请时间
    private LocalDateTime inviteTime;

}
