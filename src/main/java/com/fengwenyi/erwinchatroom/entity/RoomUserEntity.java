package com.fengwenyi.erwinchatroom.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * @author <a href="https://www.fengwenyi.com">Erwin Feng</a>
 * @since 2021-08-18
 */
@Entity
@Table(name = "t_room_user")
@Data
public class RoomUserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private String id;

    private String rid;

    private String uid;

}
