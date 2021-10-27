package com.fengwenyi.erwinchatroom.service;

import com.fengwenyi.api.result.ResponseTemplate;
import com.fengwenyi.erwinchatroom.vo.response.BroadcastMessageResponseVo;

/**
 * @author <a href="https://www.fengwenyi.com">Erwin Feng</a>
 * @since 2021-08-25
 */
public interface IBroadcastService {

    ResponseTemplate<Void> userEntryRoomTip(BroadcastMessageResponseVo responseVo);

}
