package com.fengwenyi.erwinchatroom.service.impl;

import com.fengwenyi.api.result.ResultTemplate;
import com.fengwenyi.erwinchatroom.entity.UserEntity;
import com.fengwenyi.erwinchatroom.repository.IUserRepository;
import com.fengwenyi.erwinchatroom.service.IUserService;
import com.fengwenyi.erwinchatroom.vo.request.UserInitRequestVo;
import com.fengwenyi.erwinchatroom.vo.response.UserInitResponseVo;
import com.fengwenyi.javalib.generate.IdUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author <a href="https://www.fengwenyi.com">Erwin Feng</a>
 * @since 2021-08-19
 */
@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private IUserRepository userRepository;

    @Override
    public void init(String uid) {
        UserEntity userEntity = userRepository.findById(uid).orElse(null);
        if (Objects.isNull(userEntity)) {
            userRepository.save(
                    new UserEntity()
                            .setUid(uid)
                            .setLoginTime(LocalDateTime.now()));
        }
    }

    @Override
    public ResultTemplate<UserInitResponseVo> init(UserInitRequestVo requestVo) {

        String uid = requestVo.getUid();
        if (!StringUtils.hasText(uid)) {
            uid = IdUtils.genId();
        }

        UserEntity userEntity = userRepository.findById(uid).orElse(null);

        if (Objects.isNull(userEntity)) {
            userRepository.save(new UserEntity().setUid(uid).setCreateTime(LocalDateTime.now()));
        }

        UserInitResponseVo responseVo = new UserInitResponseVo().setUid(uid);

        return ResultTemplate.success(responseVo);
    }
}
