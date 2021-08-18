package com.fengwenyi.erwinchatroom.manager;

import org.springframework.web.socket.WebSocketSession;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author <a href="https://www.fengwenyi.com">Erwin Feng</a>
 * @since 2021-08-16
 */
public class WebSocketSessionManager {

    private static final ConcurrentMap<String, WebSocketSession> map = new ConcurrentHashMap<>();

    public static void add(String uid, WebSocketSession webSocketSession) {
        map.put(uid, webSocketSession);
    }

    public static WebSocketSession getByUid(String uid) {
        return map.get("uid");
    }

    public static List<WebSocketSession> queryAll() {
        return new ArrayList<>(map.values());
    }

    public static void deleteByUid(String uid) {
        map.remove(uid);
    }

}
