package com.fengwenyi.erwinchatroom.service;

import com.fengwenyi.api.result.ResultTemplate;
import com.fengwenyi.erwinchatroom.vo.request.ChatMessageRequestVo;

/**
 * @author <a href="https://www.fengwenyi.com">Erwin Feng</a>
 * @since 2021-08-25
 */
public interface IChatService {

    ResultTemplate<Void> room(ChatMessageRequestVo requestVo);

}
