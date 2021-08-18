package com.fengwenyi.erwinchatroom.interceptor;

import com.fengwenyi.erwinchatroom.entity.UserEntity;
import com.fengwenyi.erwinchatroom.repository.IUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.net.URI;
import java.util.Map;
import java.util.Objects;

/**
 * @author <a href="https://www.fengwenyi.com">Erwin Feng</a>
 * @since 2021-08-16
 */
@Component
@Slf4j
public class ChatRoomInterceptor implements HandshakeInterceptor {

    @Autowired
    private IUserRepository userRepository;

    @Override
    public boolean beforeHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Map<String, Object> map) throws Exception {

        String query = serverHttpRequest.getURI().getQuery();
        if (!StringUtils.hasText(query)) {
            log.error("请求地址不正确");
            return false;
        }

        String sessionId = query;
        UserEntity userEntity = userRepository.findById(sessionId).orElse(null);
        if (Objects.isNull(userEntity)) {
            log.error("用户未初始化，sessionId={}", sessionId);
            return false;
        }
        /*Boolean register = userEntity.getRegister();
        if (!register) {
            log.error("用户未注册，sessionId={}", sessionId);
            return false;
        }*/

        map.put("uid", sessionId);

        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Exception e) {

    }
}
