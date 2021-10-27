package com.fengwenyi.erwinchatroom.service;

import com.fengwenyi.api.result.ResponseTemplate;

/**
 * @author <a href="https://www.fengwenyi.com">Erwin Feng</a>
 * @since 2021-08-24
 */
public interface IMsgService {

    <T> void sendAll(ResponseTemplate<T> resultTemplate);

    <T> void sendToUser(String userId, ResponseTemplate<T> resultTemplate);

    <T> void sendToRoom(String rid, ResponseTemplate<T> resultTemplate);
}
