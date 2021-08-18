package com.fengwenyi.erwinchatroom.controller;

import com.fengwenyi.api.result.ResultTemplate;
import com.fengwenyi.erwinchatroom.entity.UserEntity;
import com.fengwenyi.erwinchatroom.repository.IUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author <a href="https://www.fengwenyi.com">Erwin Feng</a>
 * @since 2021-08-18
 */
@RestController
@RequestMapping("/auth")
@Slf4j
public class AuthController {

    @Autowired
    private IUserRepository userRepository;

    // 用户登记
    @RequestMapping("/userInit")
    public ResultTemplate<?> userInit(HttpSession session) {
        session.setMaxInactiveInterval(-1);
        log.info("sessionId={}", session.getId());

        userRepository.save(new UserEntity().setUid(session.getId()));

        return ResultTemplate.success(session.getId());
    }

    // 进入房间前，
    // 判断还能否进入，
    // 判断是否需要密码 -> 返回需要输入密码

    // 验证密码
    // 失败 -> 返回密码错误
    // 成功 -> 标记用户在指定房间权限验证通过，5分钟内有效，有效次数一次

}
