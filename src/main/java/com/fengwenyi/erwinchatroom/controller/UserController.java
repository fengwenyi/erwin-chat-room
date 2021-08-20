package com.fengwenyi.erwinchatroom.controller;

import com.fengwenyi.api.result.ResultTemplate;
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
    public ResultTemplate<UserInitResponseVo> init(@RequestBody UserInitRequestVo requestVo) {
        return userService.init(requestVo);
    }

    @PostMapping("/update")
    public ResultTemplate<Void> update(@RequestBody @Validated UserRequestVo requestVo) {
        return userService.updateUser(requestVo);
    }

    @GetMapping("/test")
    public ResultTemplate<?> test() throws Exception {
        //int i = 1 / 0;
        throw new Exception("1111");
    }

}
