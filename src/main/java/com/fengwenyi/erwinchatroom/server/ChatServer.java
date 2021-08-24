package com.fengwenyi.erwinchatroom.server;

import com.fengwenyi.api.result.ResultTemplate;
import com.fengwenyi.erwinchatroom.enums.ChatContentTypeEnum;
import com.fengwenyi.erwinchatroom.service.IMsgService;
import com.fengwenyi.erwinchatroom.vo.request.ChatMessageRequestVo;
import com.fengwenyi.erwinchatroom.vo.request.MessageRequestVo;
import com.fengwenyi.javalib.convert.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @author <a href="https://www.fengwenyi.com">Erwin Feng</a>
 * @since 2021-08-24
 */
@Component
@MessageMapping("/chat")
@Slf4j
public class ChatServer {

    @Autowired
    private IMsgService msgService;

    @MessageMapping("/room")
    public ResultTemplate<?> room(ChatMessageRequestVo requestVo) {
        log.debug("请求参数={}", JsonUtils.convertString(requestVo));
        String rid = requestVo.getRid();
        MessageRequestVo message = requestVo.getMessage();
        Integer contentType = message.getContentType();
        String content = message.getContent();
        if (Objects.isNull(ChatContentTypeEnum.getType(contentType))) {
            return ResultTemplate.fail("该格式暂不支持");
        }
        msgService.sendToRoom(rid, ResultTemplate.success(content));
        return ResultTemplate.success();
    }

}
