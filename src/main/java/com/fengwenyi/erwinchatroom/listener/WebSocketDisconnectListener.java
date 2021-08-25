package com.fengwenyi.erwinchatroom.listener;

import com.fengwenyi.api.result.ResultTemplate;
import com.fengwenyi.erwinchatroom.entity.RoomEntity;
import com.fengwenyi.erwinchatroom.entity.RoomUserEntity;
import com.fengwenyi.erwinchatroom.entity.UserEntity;
import com.fengwenyi.erwinchatroom.enums.BroadcastContentTypeEnum;
import com.fengwenyi.erwinchatroom.enums.MessageTypeEnum;
import com.fengwenyi.erwinchatroom.repository.IRoomRepository;
import com.fengwenyi.erwinchatroom.repository.IRoomUserRepository;
import com.fengwenyi.erwinchatroom.repository.IUserRepository;
import com.fengwenyi.erwinchatroom.service.IMsgService;
import com.fengwenyi.erwinchatroom.vo.response.BroadcastMessageResponseVo;
import com.fengwenyi.erwinchatroom.vo.response.ContentResponseVo;
import com.fengwenyi.javalib.convert.DateTimeUtils;
import com.fengwenyi.javalib.convert.JsonUtils;
import com.fengwenyi.javalib.generate.IdUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.data.domain.Example;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * @author <a href="https://www.fengwenyi.com">Erwin Feng</a>
 * @since 2021-08-25
 */
@Slf4j
@Component
public class WebSocketDisconnectListener implements ApplicationListener<SessionDisconnectEvent> {

    @Autowired
    private IRoomRepository roomRepository;

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IRoomUserRepository roomUserRepository;

    @Autowired
    private IMsgService msgService;

    @Override
    public void onApplicationEvent(SessionDisconnectEvent event) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
        String sessionId = accessor.getSessionId();

        Optional<RoomUserEntity> optionalRoomUser = roomUserRepository.findById(sessionId);
        if (!optionalRoomUser.isPresent()) {
            return;
        }

        RoomUserEntity roomUserEntity = optionalRoomUser.get();

        String rid = roomUserEntity.getRid();
        String uid = roomUserEntity.getUid();
        log.debug(rid);
        log.debug(uid);

        log.debug("离开房间：sessionId={}, rid={}, uid={}", sessionId, rid, uid);

        Optional<RoomEntity> optionalRoom = roomRepository.findById(rid);
        if (optionalRoom.isPresent()) {
            RoomEntity roomEntity = optionalRoom.get();
            roomRepository.save(roomEntity.setUserCount(roomEntity.getUserCount() - 1));
        }

        roomUserRepository.deleteById(sessionId);

        UserEntity userEntity = userRepository.findById(uid).orElse(null);

        // 消息通知
        ContentResponseVo contentResponseVo = new ContentResponseVo();
        contentResponseVo.setContentType(BroadcastContentTypeEnum.TIP_USER_ENTER_ROOM.getCode());
        contentResponseVo.setContent(userEntity.getNickname() + " 离开房间");

        BroadcastMessageResponseVo broadcastMessageResponseVo = new BroadcastMessageResponseVo();
        broadcastMessageResponseVo.setMessageType(MessageTypeEnum.BROADCAST.getCode());
        broadcastMessageResponseVo.setMessage(contentResponseVo);
        broadcastMessageResponseVo.setTimestamp(System.currentTimeMillis() / 1000);
        broadcastMessageResponseVo.setTimeStr(DateTimeUtils.format(LocalDateTime.now(), "HH:mm"));
        msgService.sendToRoom(rid, ResultTemplate.success(broadcastMessageResponseVo));
    }
}
