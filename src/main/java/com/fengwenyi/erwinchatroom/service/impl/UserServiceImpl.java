package com.fengwenyi.erwinchatroom.service.impl;

import com.fengwenyi.erwinchatroom.entity.UserEntity;
import com.fengwenyi.erwinchatroom.repository.IUserRepository;
import com.fengwenyi.erwinchatroom.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
