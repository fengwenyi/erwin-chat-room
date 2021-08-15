package com.fengwenyi.erwinchatroom.controller;

import com.fengwenyi.api.result.ResultTemplate;
import com.fengwenyi.erwinchatroom.domain.UserModel;
import com.fengwenyi.erwinchatroom.enums.UserStatusEnum;
import com.fengwenyi.erwinchatroom.utils.UserUtils;
import com.fengwenyi.erwinchatroom.vo.request.UserRegisterRequestVo;
import com.fengwenyi.erwinchatroom.vo.response.UserRegisterResponseVo;
import com.fengwenyi.javalib.generate.IdUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author <a href="https://www.fengwenyi.com">Erwin Feng</a>
 * @since 2021-08-14
 */
@RestController
@RequestMapping("/business")
@Slf4j
public class BusinessController {

    @PostMapping("/register")
    public ResultTemplate<UserRegisterResponseVo> register(@RequestBody @Validated UserRegisterRequestVo requestVo, HttpSession httpSession) {
        String uid = httpSession.getId();
        UserModel userModel = UserUtils.queryByUid(uid);
        if (Objects.isNull(userModel)) {
            UserUtils.add(
                    new UserModel()
                            .setCreateTime(LocalDateTime.now())
                            .setUid(uid)
                            .setNickname(requestVo.getNickname())
                            .setStatus(UserStatusEnum.OFFLINE));
        } else {
            if (!requestVo.getNickname().equals(userModel.getNickname())) {
                userModel.setUpdateTime(LocalDateTime.now())
                        .setNickname(requestVo.getNickname());
            }
        }
        return ResultTemplate.success(new UserRegisterResponseVo().setUid(uid));
    }

}
