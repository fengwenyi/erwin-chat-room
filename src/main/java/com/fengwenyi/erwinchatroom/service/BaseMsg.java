package com.fengwenyi.erwinchatroom.service;

import org.springframework.web.socket.WebSocketSession;

import javax.websocket.Session;
import java.io.IOException;

/**
 * @author Wenyi Feng
 */
public class BaseMsg {

    protected void sendMsg (Session session, String message)
            throws IOException
    {
        session.getBasicRemote().sendText(message);
        WebSocketSession webSocketSession;
    }

}
