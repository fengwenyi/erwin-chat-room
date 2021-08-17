package com.fengwenyi.erwinchatroom.handler;

import com.fengwenyi.erwinchatroom.entity.UserEntity;
import com.fengwenyi.erwinchatroom.repository.IUserRepository;
import com.fengwenyi.javalib.generate.IdUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Map;

/**
 * @author <a href="https://www.fengwenyi.com">Erwin Feng</a>
 * @since 2021-08-16
 */
@Component
@Slf4j
public class ChatRoomWeSocketHandler extends TextWebSocketHandler {

    @Autowired
    private IUserRepository userRepository;

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        //super.handleTextMessage(session, message);

        Map<String, Object> attributes = session.getAttributes();
        Object uid = attributes.get("uid");
        Object rid = attributes.get("rid");

        log.info(message.getPayload());
        log.info(session.getId());
        //log.info();

        UserEntity userEntity = new UserEntity().setUid(IdUtils.genId()).setNickname(message.getPayload());

        userRepository.save(userEntity);

        //session.getHandshakeHeaders().set(HttpHeaders.COOKIE, userEntity.getUid());

        session.sendMessage(new TextMessage(userEntity.getUid()));

    }
}
