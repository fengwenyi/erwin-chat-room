package com.fengwenyi.erwinchatroom.repository;

import com.fengwenyi.erwinchatroom.entity.RoomInviteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author <a href="https://www.fengwenyi.com">Erwin Feng</a>
 * @since 2021-08-30
 */
public interface IRoomInviteRepository extends JpaRepository<RoomInviteEntity, String> {
}
