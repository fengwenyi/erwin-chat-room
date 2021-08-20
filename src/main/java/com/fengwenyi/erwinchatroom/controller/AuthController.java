package com.fengwenyi.erwinchatroom.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author <a href="https://www.fengwenyi.com">Erwin Feng</a>
 * @since 2021-08-18
 */
@RestController
@RequestMapping("/auth")
@Slf4j
public class AuthController {

    // 进入房间前，
    // 判断还能否进入，
    // 判断是否需要密码 -> 返回需要输入密码

    // 验证密码
    // 失败 -> 返回密码错误
    // 成功 -> 标记用户在指定房间权限验证通过，5分钟内有效，有效次数一次

}
