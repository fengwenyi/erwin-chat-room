package com.fengwenyi.erwinchatroom.server;

import com.fengwenyi.api.result.ResultTemplate;
import com.fengwenyi.erwinchatroom.service.IChatService;
import com.fengwenyi.erwinchatroom.vo.request.ChatMessageRequestVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author <a href="https://www.fengwenyi.com">Erwin Feng</a>
 * @since 2021-08-24
 */
@RestController
@MessageMapping("/chat")
public class ChatServer {

    @Autowired
    private IChatService chatService;

    @MessageMapping("/room")
    public ResultTemplate<?> room(ChatMessageRequestVo requestVo) {
        return chatService.room(requestVo);
    }

}
