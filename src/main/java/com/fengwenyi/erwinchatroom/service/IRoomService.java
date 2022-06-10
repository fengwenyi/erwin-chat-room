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
    ResponseTemplate<RoomResponseVo> create(RoomRequestVo requestVo);

    /**
     * 查询房间
     * @param pageRequest
     * @return
     */
    ResponseTemplate<PageResponseVo<List<RoomResponseVo>>> getPage(PageRequestVo pageRequest);

    /**
     * 根据id查询
     * @param rid 房间id
     * @return
     */
    ResponseTemplate<RoomResponseVo> get(String rid);

    /**
     * 房间用户统计
     * @param rid
     * @return
     */
    ResponseTemplate<String> getUserCount(String rid);

    /**
     * 获取房间用户列表
     * @param rid 房间id
     * @return 用户列表
     */
    ResponseTemplate<List<UserResponseVo>> getUserList(String rid);

    void deleteEmpty();
}
