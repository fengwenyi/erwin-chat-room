package com.fengwenyi.erwinchatroom.service;

import com.fengwenyi.api.result.ResponseTemplate;
import com.fengwenyi.erwinchatroom.vo.request.ChatMessageRequestVo;

/**
 * @author <a href="https://www.fengwenyi.com">Erwin Feng</a>
 * @since 2021-08-25
 */
public interface IChatService {

    ResponseTemplate<Void> room(ChatMessageRequestVo requestVo);

}
