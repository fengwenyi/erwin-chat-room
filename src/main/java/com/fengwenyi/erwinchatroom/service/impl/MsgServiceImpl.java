package com.fengwenyi.erwinchatroom.service.impl;

import com.fengwenyi.api.result.ResultTemplate;
import com.fengwenyi.erwinchatroom.service.IMsgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

/**
 * @author <a href="https://www.fengwenyi.com">Erwin Feng</a>
 * @since 2021-08-24
 */
@Service
public class MsgServiceImpl implements IMsgService {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Override
    public <T> void sendAll(ResultTemplate<T> resultTemplate) {
        messagingTemplate.convertAndSend("/topic/getResponse", resultTemplate);
    }

    @Override
    public <T> void sendToUser(String userId, ResultTemplate<T> resultTemplate) {
        messagingTemplate.convertAndSendToUser(userId, "/topic/getResponse", resultTemplate);
    }

    @Override
    public <T> void sendToRoom(String rid, ResultTemplate<T> resultTemplate) {
        messagingTemplate.convertAndSend("/room/" + rid, resultTemplate);
    }
}
