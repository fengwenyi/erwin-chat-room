package com.fengwenyi.erwinchatroom.service.impl;

import com.fengwenyi.api.result.ResultTemplate;
import com.fengwenyi.erwinchatroom.service.IChatService;
import com.fengwenyi.erwinchatroom.service.IMsgService;
import com.fengwenyi.erwinchatroom.vo.request.ChatMessageRequestVo;
import com.fengwenyi.erwinchatroom.vo.response.ChatMessageResponseVo;
import com.fengwenyi.erwinchatroom.vo.response.ContentResponseVo;
import com.fengwenyi.javalib.convert.DateTimeUtils;
import com.fengwenyi.javalib.convert.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * @author <a href="https://www.fengwenyi.com">Erwin Feng</a>
 * @since 2021-08-25
 */
@Service
@Slf4j
public class ChatServiceImpl implements IChatService {

    @Autowired
    private IMsgService msgService;

    @Override
    public ResultTemplate<Void> room(ChatMessageRequestVo requestVo) {

        log.debug("请求参数={}", JsonUtils.convertString(requestVo));

        ChatMessageResponseVo chatMessageResponseVo = new ChatMessageResponseVo();

        ContentResponseVo contentResponseVo = new ContentResponseVo();
        contentResponseVo.setContentType(requestVo.getMessage().getContentType());
        contentResponseVo.setContent(requestVo.getMessage().getContent());

        chatMessageResponseVo.setMessage(contentResponseVo);

        chatMessageResponseVo.setSender(null);
        chatMessageResponseVo.setSelf(false);
        chatMessageResponseVo.setTimestamp(System.currentTimeMillis() / 1000);
        chatMessageResponseVo.setTimeStr(DateTimeUtils.format(LocalDateTime.now(), "HH:mm"));

        ResultTemplate<?> result = ResultTemplate.success(chatMessageResponseVo);

        log.debug("发送的消息：{}", JsonUtils.convertString(result));

        msgService.sendToRoom(requestVo.getRid(), result);

        return ResultTemplate.success();
    }
}
