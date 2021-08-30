package com.fengwenyi.erwinchatroom.handler;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.util.StringUtils;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.Map;

/**
 * @author <a href="https://www.fengwenyi.com">Erwin Feng</a>
 * @since 2021-08-30
 */
public class PrincipalHandshakeHandler extends HttpSessionHandshakeInterceptor {

    /*@Override
    protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler,
                                      Map<String, Object> attributes) {
        HttpSession httpSession = getSession(request);
        String token = (String) httpSession.getAttribute("token");

        if (StringUtils.hasText(token)) {
            return new ExportPrincipal(token);
        }
        return null;
    }*/

    private HttpSession getSession(ServerHttpRequest request) {
        ServletServerHttpRequest serverRequest = (ServletServerHttpRequest) request;
        return serverRequest.getServletRequest().getSession(true);
    }

}
