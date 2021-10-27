package com.fengwenyi.erwinchatroom.listener;

import com.fengwenyi.api.result.ResponseTemplate;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * @author <a href="https://www.fengwenyi.com">Erwin Feng</a>
 * @since 2021-08-25
 */
@Component
@Slf4j
public class WebSocketConnectListener implements ApplicationListener<SessionConnectEvent> {

    @Autowired
    private IRoomRepository roomRepository;

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IRoomUserRepository roomUserRepository;

    @Autowired
    private IMsgService msgService;

    @Override
    public void onApplicationEvent(SessionConnectEvent sessionConnectEvent) {
//        Object source = sessionConnectEvent.getSource();
//        log.debug(JsonUtils.convertString(source));
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(sessionConnectEvent.getMessage());
//        log.debug(JsonUtils.convertString(accessor));
        String sessionId = accessor.getSessionId();
//        log.info("sessionId: {} 已连接", sessionId);
        //accessor.
        String rid = accessor.getNativeHeader("rid").get(0);
        String uid = accessor.getNativeHeader("uid").get(0);

//        log.debug("进入房间：sessionId={}, rid={}, uid={}", sessionId, rid, uid);

        Optional<RoomEntity> optionalRoom = roomRepository.findById(rid);
        if (optionalRoom.isPresent()) {
            RoomEntity roomEntity = optionalRoom.get();
            roomRepository.save(roomEntity.setUserCount(roomEntity.getUserCount() + 1));
        }

        roomUserRepository.save(new RoomUserEntity().setSessionId(sessionId).setRid(rid).setUid(uid).setEntryTime(LocalDateTime.now()));

        UserEntity userEntity = userRepository.findById(uid).orElse(null);

        // 消息通知
        ContentResponseVo contentResponseVo = new ContentResponseVo();
        contentResponseVo.setContentType(BroadcastContentTypeEnum.TIP_USER_ENTER_ROOM.getCode());
        contentResponseVo.setContent(userEntity.getNickname() + " 进入房间");

        BroadcastMessageResponseVo broadcastMessageResponseVo = new BroadcastMessageResponseVo();
        broadcastMessageResponseVo.setMessageType(MessageTypeEnum.BROADCAST.getCode());
        broadcastMessageResponseVo.setMessage(contentResponseVo);
        broadcastMessageResponseVo.setTimestamp(System.currentTimeMillis() / 1000);
        broadcastMessageResponseVo.setTimeStr(DateTimeUtils.format(LocalDateTime.now(), "HH:mm"));
        msgService.sendToRoom(rid, ResponseTemplate.success(broadcastMessageResponseVo));
    }
}