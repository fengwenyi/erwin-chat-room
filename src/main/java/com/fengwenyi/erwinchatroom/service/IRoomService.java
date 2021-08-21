package com.fengwenyi.erwinchatroom.service;

import com.fengwenyi.api.result.PageRequest;
import com.fengwenyi.api.result.PageTemplate;
import com.fengwenyi.api.result.ResultTemplate;
import com.fengwenyi.erwinchatroom.vo.request.RoomRequestVo;
import com.fengwenyi.erwinchatroom.vo.response.RoomResponseVo;

import java.util.List;

/**
 * @author <a href="https://www.fengwenyi.com">Erwin Feng</a>
 * @since 2021-08-21
 */
public interface IRoomService {

    /**
     * 创建房间
     * @param requestVo
     * @return
     */
    ResultTemplate<RoomResponseVo> create(RoomRequestVo requestVo);

    /**
     * 查询房间
     * @param pageRequest
     * @return
     */
    ResultTemplate<PageTemplate<List<RoomResponseVo>>> getPage(PageRequest<?> pageRequest);
}
