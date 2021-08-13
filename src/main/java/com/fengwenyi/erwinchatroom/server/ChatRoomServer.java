package com.fengwenyi.erwinchatroom.server;

import com.fengwenyi.erwinchatroom.domain.UserModel;
import com.fengwenyi.erwinchatroom.service.impl.MsgServiceImpl;
import com.fengwenyi.erwinchatroom.utils.UserUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.server.standard.SpringConfigurator;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Objects;

/**
 * @author <a href="https://www.fengwenyi.com">Erwin Feng</a>
 * @since 2021-08-12
 */
@Component
@ServerEndpoint(value = "/chat-room")
@Slf4j
public class ChatRoomServer extends MsgServiceImpl {

    private String sessionId;

    private String oldSessionId;

    @OnOpen
    public void open(WebSocketSession session) {

        //session.setMaxIdleTimeout(-1);

        UserModel userModel = UserUtils.queryById(session.getId());
        if (Objects.nonNull(userModel)) {
            sessionId = oldSessionId;
        } else {
            sessionId = session.getId();
            userModel = new UserModel()
                    .setId(session.getId())
                    //.setUsername(username)
                    //.setSession(session)
                    ;
            UserUtils.add(userModel);
        }

        // 欢迎 xxx 用户加入聊天室
        String message = String.format("欢迎 %s 用户加入聊天室", session.getId());
        //sendBroadcast(message);

    }

    @OnMessage
    public void message(Session session, String message) {
        sendMsgAll(session.getId(), message);
    }

    @OnClose
    public void close(Session session) {

        oldSessionId = sessionId;

        // xxx 用户离开了聊天室
        UserModel userModel = UserUtils.queryById(session.getId());
        String message = String.format("%s 用户离开了聊天室", userModel.getUsername());

        UserUtils.deleteById(session.getId());

        sendBroadcast(message);
    }

    @OnError
    public void error(Session session, Throwable error) {
        log.error("发生错误, sessionId:" + session.getId());
        error.printStackTrace();
        try {
            session.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
