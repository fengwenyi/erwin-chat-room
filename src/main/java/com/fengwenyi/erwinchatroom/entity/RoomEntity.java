package com.fengwenyi.erwinchatroom.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * @author <a href="https://www.fengwenyi.com">Erwin Feng</a>
 * @since 2021-08-18
 */
@Entity
@Table(name = "t_user")
@Data
public class RoomEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private String uid;

    private String nickname;

    private Boolean needPassword;

    private String password;

}
