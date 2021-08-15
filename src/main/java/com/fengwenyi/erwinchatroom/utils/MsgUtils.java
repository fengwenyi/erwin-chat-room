package com.fengwenyi.erwinchatroom.utils;

import com.fengwenyi.api.result.ResultTemplate;
import com.fengwenyi.erwinchatroom.bo.MsgBo;
import com.fengwenyi.erwinchatroom.domain.UserChatRoomModel;
import com.fengwenyi.erwinchatroom.domain.UserModel;
import com.fengwenyi.erwinchatroom.enums.FromTypeEnum;
import com.fengwenyi.erwinchatroom.enums.MsgTypeEnum;
import com.fengwenyi.erwinchatroom.vo.response.ResponseVo;
import com.fengwenyi.erwinchatroom.vo.response.UserResponseVo;
import com.fengwenyi.javalib.convert.DateTimeUtils;
import com.fengwenyi.javalib.convert.JsonUtils;
import org.springframework.util.CollectionUtils;

import javax.websocket.Session;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author <a href="https://www.fengwenyi.com">Erwin Feng</a>
 * @since 2021-08-15
 */
public class MsgUtils {

    public static void sendUserError(Session session, String msg) {
        send(session, ResultTemplate.fail(msg));
    }

    public static void sendAll(String uid, String message) {
        UserChatRoomModel sender = UserChatRoomUtils.queryByUid(uid);
        sendAll(
                new MsgBo()
                        .setMsgTypeEnum(MsgTypeEnum.CHAT)
                        .setMessage(message)
                        .setFromTypeEnum(FromTypeEnum.USER)
                        .setSender(sender)
                        .setReceiver(null)
                        .setTimestamp(System.currentTimeMillis() / 1000)
                        .setTimeStr(DateTimeUtils.format(LocalDateTime.now(), "HH:mm")));
    }

    public static void sendBroadcast(String message) {
        sendAll(
                new MsgBo()
                        .setMsgTypeEnum(MsgTypeEnum.BROADCAST)
                        .setMessage(message)
                        .setFromTypeEnum(FromTypeEnum.SYSTEM)
                        .setSender(null)
                        .setReceiver(null));
    }

    public static void sendConn(String message) {
        sendAll(
                new MsgBo()
                        .setMsgTypeEnum(MsgTypeEnum.CONN)
                        .setMessage(message)
                        .setFromTypeEnum(FromTypeEnum.SYSTEM)
                        .setSender(null)
                        .setReceiver(null));
    }

    public static void sendUserOnLineUserList(UserChatRoomModel userChatRoomModel) {
        sendUser(userChatRoomModel,
                new MsgBo()
                        .setMsgTypeEnum(MsgTypeEnum.ONLINE_USER_LIST)
                        //.setMessage(message)
                        .setFromTypeEnum(FromTypeEnum.SYSTEM)
                        .setSender(null)
                        .setReceiver(null)
                        .setOnlineUserList(UserChatRoomUtils.queryAll()));
    }

    public static void sendAllUserUpLine(UserChatRoomModel userChatRoomModel) {
        sendAll(new MsgBo()
                        .setMsgTypeEnum(MsgTypeEnum.USER_UP_LINE)
                        //.setMessage(message)
                        .setFromTypeEnum(FromTypeEnum.SYSTEM)
                        .setSender(null)
                        .setReceiver(null)
                        .setUserUpLine(userChatRoomModel));
    }

    private static void sendAll(MsgBo bo) {
        List<UserChatRoomModel> userChatRoomModelList = UserChatRoomUtils.queryAll();
        UserChatRoomModel senderModel = bo.getSender();
        UserChatRoomModel userUpLineModel = bo.getUserUpLine();
        UserResponseVo userUpLine = new UserResponseVo();
        if (Objects.nonNull(bo.getUserUpLine())) {
            userUpLine.setUid(userUpLineModel.getUid())
                    .setNickname(userUpLineModel.getNickname())
                    .setTimeStr(DateTimeUtils.format(userUpLineModel.getTime(), "HH:mm:ss"));
        }
        for (UserChatRoomModel model : userChatRoomModelList) {
            UserResponseVo sender = new UserResponseVo();
            if (Objects.nonNull(senderModel)) {
                sender
                        .setUid(senderModel.getUid())
                        .setNickname(senderModel.getNickname())
                        .setTimeStr(DateTimeUtils.format(senderModel.getTime(), "HH:mm:ss"))
                ;
            }
            if (Objects.equals(bo.getMsgTypeEnum().getCode(), MsgTypeEnum.USER_UP_LINE.getCode())
                    && model.getUid().equals(userUpLine.getUid())) {
                continue;
            }
            ResultTemplate<ResponseVo> result = ResultTemplate.success(
                    new ResponseVo()
                            .setFromType(bo.getFromTypeEnum().getCode())
                            .setSender(sender)
                            .setMsgType(bo.getMsgTypeEnum().getCode())
                            .setMessage(bo.getMessage())
                            .setTimestamp(bo.getTimestamp())
                            .setTimeStr(bo.getTimeStr())
                            .setSelf(model.getUid().equals(sender.getUid()))
                            .setUserUpLine(userUpLine));
            send(model.getSession(), result);
        }
    }

    private static void sendUser(UserChatRoomModel userChatRoomModel, MsgBo bo) {
        UserChatRoomModel senderModel = bo.getSender();
        List<UserResponseVo> onlineUserList = CollectionUtils.isEmpty(bo.getOnlineUserList()) ? null :
                bo.getOnlineUserList().stream()
                        .map(model ->
                                new UserResponseVo()
                                        .setUid(model.getUid())
                                        .setNickname(model.getNickname())
                                        .setTimeStr(DateTimeUtils.format(model.getTime(), "HH:mm:ss")))
                        .collect(Collectors.toList());
        UserResponseVo sender = new UserResponseVo();
        if (Objects.nonNull(senderModel)) {
            sender
                    .setUid(senderModel.getUid())
                    .setNickname(senderModel.getNickname())
                    .setTimeStr(DateTimeUtils.format(senderModel.getTime(), "HH:mm:ss"))
            ;
        }
        ResultTemplate<ResponseVo> result = ResultTemplate.success(
                new ResponseVo()
                        .setFromType(bo.getFromTypeEnum().getCode())
                        .setSender(sender)
                        .setMsgType(bo.getMsgTypeEnum().getCode())
                        .setMessage(bo.getMessage())
                        .setTimestamp(bo.getTimestamp())
                        .setTimeStr(bo.getTimeStr())
                        .setSelf(userChatRoomModel.getUid().equals(sender.getUid()))
                        .setOnlineUserList(onlineUserList));
        send(userChatRoomModel.getSession(), result);
    }

    private static <T> void send(Session session, ResultTemplate<T> result) {
        try {
            session.getBasicRemote().sendText(JsonUtils.convertString(result));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

