package com.fengwenyi.erwinchatroom.server;

import com.fengwenyi.erwinchatroom.constant.Message;
import com.fengwenyi.erwinchatroom.domain.UserChatRoomModel;
import com.fengwenyi.erwinchatroom.domain.UserModel;
import com.fengwenyi.erwinchatroom.enums.UserStatusEnum;
import com.fengwenyi.erwinchatroom.utils.MsgUtils;
import com.fengwenyi.erwinchatroom.utils.UserChatRoomUtils;
import com.fengwenyi.erwinchatroom.utils.UserUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author <a href="https://www.fengwenyi.com">Erwin Feng</a>
 * @since 2021-08-12
 */
@Component
@ServerEndpoint(value = "/chat-room/{uid}")
@Slf4j
public class ChatRoomServer {

    @OnOpen
    public void open(Session session, @PathParam("uid") String uid) {
        UserModel userModel = UserUtils.queryByUid(uid);
        if (Objects.nonNull(userModel)) {
            UserChatRoomModel userChatRoomModel = UserChatRoomUtils.queryByUid(uid);
            if (Objects.isNull(userChatRoomModel)) {
                UserChatRoomUtils.add(
                        new UserChatRoomModel()
                                .setUid(uid)
                                .setNickname(userModel.getNickname())
                                .setSession(session)
                                .setTime(LocalDateTime.now()));

                UserUtils.updateStatusByUid(uid, UserStatusEnum.ONLINE);

                // 欢迎 xxx 用户加入聊天室
                String message = String.format(Message.CONN, userModel.getNickname());

                MsgUtils.sendConn(message);

                // 有新用户上线，给所有人推送
                userChatRoomModel = UserChatRoomUtils.queryByUid(uid);
                MsgUtils.sendAllUserUpLine(userChatRoomModel);

                // 给新用户推所有人
            } else {
                UserChatRoomUtils.updateByUid(
                        userChatRoomModel
                                .setSession(session)
                                .setTime(LocalDateTime.now()));
                // 说明是刷新，只给一个人推送列表
            }
            MsgUtils.sendUserOnLineUserList(userChatRoomModel);
        }
    }

    @OnMessage
    public void message(@PathParam("uid") String uid, String message) {
        MsgUtils.sendAll(uid, message);
    }

    @OnClose
    public void close(Session session, @PathParam("uid") String uid) {

        UserChatRoomUtils.deleteByUid(uid);
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
