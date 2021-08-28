package com.fengwenyi.erwinchatroom.service;

import com.fengwenyi.api.result.ResultTemplate;
import com.fengwenyi.erwinchatroom.vo.request.UserInitRequestVo;
import com.fengwenyi.erwinchatroom.vo.request.UserRequestVo;
import com.fengwenyi.erwinchatroom.vo.response.UserInitResponseVo;

/**
 * @author <a href="https://www.fengwenyi.com">Erwin Feng</a>
 * @since 2021-08-19
 */
public interface IUserService {

    /**
     * 用户初始化
     * @param requestVo
     * @return
     */
    ResultTemplate<UserInitResponseVo> init(UserInitRequestVo requestVo);

    /**
     * 更新用户
     * @param requestVo
     * @return
     */
    ResultTemplate<Void> update(UserRequestVo requestVo);
}
