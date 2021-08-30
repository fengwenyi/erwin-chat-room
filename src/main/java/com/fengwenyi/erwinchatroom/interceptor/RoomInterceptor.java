package com.fengwenyi.erwinchatroom.interceptor;

import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

/**
 * @author <a href="https://www.fengwenyi.com">Erwin Feng</a>
 * @since 2021-08-30
 */
@Component
public class RoomInterceptor implements ChannelInterceptor {

    @Override
    public boolean preReceive(MessageChannel channel) {
        return false;
    }
}
