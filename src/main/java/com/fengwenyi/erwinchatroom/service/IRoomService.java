package com.fengwenyi.erwinchatroom.service;

import com.fengwenyi.api.result.*;
import com.fengwenyi.erwinchatroom.vo.request.RoomRequestVo;
import com.fengwenyi.erwinchatroom.vo.response.RoomResponseVo;
import com.fengwenyi.erwinchatroom.vo.response.UserResponseVo;

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
    ResultTemplate<PageResponseVo<List<RoomResponseVo>>> getPage(PageRequestVo<?> pageRequest);

    /**
     * 根据id查询
     * @param rid 房间id
     * @return
     */
    ResultTemplate<RoomResponseVo> get(String rid);

    /**
     * 房间用户统计
     * @param rid
     * @return
     */
    ResultTemplate<String> getUserCount(String rid);

    /**
     * 获取房间用户列表
     * @param rid 房间id
     * @return 用户列表
     */
    ResultTemplate<List<UserResponseVo>> getUserList(String rid);

    void deleteEmpty();
}
