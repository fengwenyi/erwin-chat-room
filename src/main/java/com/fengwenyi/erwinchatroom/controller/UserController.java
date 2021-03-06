package com.fengwenyi.erwinchatroom.controller;

import com.fengwenyi.api.result.ResponseTemplate;
import com.fengwenyi.erwinchatroom.service.IUserService;
import com.fengwenyi.erwinchatroom.vo.request.UserInitRequestVo;
import com.fengwenyi.erwinchatroom.vo.request.UserRequestVo;
import com.fengwenyi.erwinchatroom.vo.response.UserInitResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author <a href="https://www.fengwenyi.com">Erwin Feng</a>
 * @since 2021-08-20
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService userService;

    // 用户初始化
    @PostMapping("/init")
    public ResponseTemplate<UserInitResponseVo> init(@RequestBody UserInitRequestVo requestVo) {
        return userService.init(requestVo);
    }

    @PostMapping("/update")
    public ResponseTemplate<Void> update(@RequestBody @Validated UserRequestVo requestVo) {
        return userService.update(requestVo);
    }

}
