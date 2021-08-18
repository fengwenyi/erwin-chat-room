package com.fengwenyi.erwinchatroom.config;

import com.fengwenyi.erwinchatroom.handler.ChatWeSocketHandler;
import com.fengwenyi.erwinchatroom.handler.RoomWeSocketHandler;
import com.fengwenyi.erwinchatroom.interceptor.ChatRoomInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * @author <a href="https://www.fengwenyi.com">Erwin Feng</a>
 * @since 2021-08-12
 */
@Configuration
@EnableWebSocket
public class WebSocketConfiguration implements WebSocketConfigurer {

    private ChatWeSocketHandler chatWeSocketHandler;

    private RoomWeSocketHandler roomWeSocketHandler;

    private ChatRoomInterceptor chatRoomInterceptor;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(roomWeSocketHandler, "/room")
                .addInterceptors(chatRoomInterceptor)
                .setAllowedOrigins("*");

        registry.addHandler(chatWeSocketHandler, "/chat").withSockJS();
    }

    @Autowired
    public void setChatWeSocketHandler(ChatWeSocketHandler chatWeSocketHandler) {
        this.chatWeSocketHandler = chatWeSocketHandler;
    }

    @Autowired
    public void setRoomWeSocketHandler(RoomWeSocketHandler roomWeSocketHandler) {
        this.roomWeSocketHandler = roomWeSocketHandler;
    }

    @Autowired
    public void setChatRoomInterceptor(ChatRoomInterceptor chatRoomInterceptor) {
        this.chatRoomInterceptor = chatRoomInterceptor;
    }
}
