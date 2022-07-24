package com.fengwenyi.erwinchatroom.interceptor;

import com.fengwenyi.apistarter.utils.Asserts;
import com.fengwenyi.erwinchatroom.entity.RoomEntity;
import com.fengwenyi.erwinchatroom.entity.UserEntity;
import com.fengwenyi.erwinchatroom.repository.IRoomRepository;
import com.fengwenyi.erwinchatroom.repository.IUserRepository;
import com.fengwenyi.erwinchatroom.utils.CacheKeyUtils;
import com.github.benmanes.caffeine.cache.Cache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Optional;

/**
 * @author <a href="https://www.fengwenyi.com">Erwin Feng</a>
 * @since 2021-08-16
 */
@Component
@Slf4j
public class ChatRoomInterceptor implements HandshakeInterceptor {

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IRoomRepository roomRepository;

    @Autowired
    private Cache<Object, Object> cache;

    @Override
    public boolean beforeHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Map<String, Object> map) throws Exception {

//        String query = serverHttpRequest.getURI().getQuery();
//        log.info(query);

        ServletServerHttpRequest shr = (ServletServerHttpRequest) serverHttpRequest;
        HttpServletRequest servletRequest = shr.getServletRequest();
        String rid = servletRequest.getParameter("rid");
        String uid = servletRequest.getParameter("uid");
        String token = servletRequest.getParameter("token");

        Optional<UserEntity> optionalUser = userRepository.findById(uid);
        if (!optionalUser.isPresent()) {
            // Asserts.fail("用户ID不正确");
            //log.error("用户ID不正确");
            return false;
        }

        Optional<RoomEntity> optionalRoom = roomRepository.findById(rid);
        if (!optionalRoom.isPresent()) {
            // Asserts.fail("房间ID不正确");
            //log.error("房间ID不正确");
            return false;
        }

        RoomEntity roomEntity = optionalRoom.get();
        if (roomEntity.getNeedPassword()) {
            if (!roomEntity.getCreateUserUid().equals(uid)) {
                if (StringUtils.hasText(token)) {
                    String authToken = (String) cache.getIfPresent(CacheKeyUtils.genRoomUserAuthKey(rid, uid));
                    if (!token.equals(authToken)) {
                        // Asserts.fail("房间认证失败");
                        //log.error("房间认证失败");
                        return false;
                    }
                } else {
                    // Asserts.fail("房间需要认证");
                    //log.error("房间需要认证");
                    return false;
                }
            }
        }

        /*
        if (!StringUtils.hasText(query)) {
            log.error("请求地址不正确");
//            Asserts.fail("请求地址不正确");
            return false;
        }*/

        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Exception e) {

    }
}
