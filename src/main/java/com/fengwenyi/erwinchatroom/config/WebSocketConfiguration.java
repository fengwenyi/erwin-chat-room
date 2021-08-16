package com.fengwenyi.erwinchatroom.config;

import com.fengwenyi.erwinchatroom.handler.ChatRoomWeSocketHandler;
import com.fengwenyi.erwinchatroom.interceptor.ChatRoomInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @author <a href="https://www.fengwenyi.com">Erwin Feng</a>
 * @since 2021-08-12
 */
@Configuration
@EnableWebSocket
public class WebSocketConfiguration implements WebSocketConfigurer {

    @Autowired
    private ChatRoomWeSocketHandler chatRoomWeSocketHandler;
    @Autowired
    private ChatRoomInterceptor chatRoomInterceptor;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry
                .addHandler(chatRoomWeSocketHandler, "")
                .addInterceptors(chatRoomInterceptor)
                .setAllowedOrigins("*");
    }

}
