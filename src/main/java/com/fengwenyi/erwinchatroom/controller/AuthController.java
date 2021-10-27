package com.fengwenyi.erwinchatroom.controller;

import com.fengwenyi.api.result.ResponseTemplate;
import com.fengwenyi.erwinchatroom.entity.RoomEntity;
import com.fengwenyi.erwinchatroom.entity.UserEntity;
import com.fengwenyi.erwinchatroom.repository.IRoomRepository;
import com.fengwenyi.erwinchatroom.repository.IUserRepository;
import com.fengwenyi.erwinchatroom.utils.CacheKeyUtils;
import com.fengwenyi.erwinchatroom.utils.PasswordUtils;
import com.fengwenyi.erwinchatroom.vo.request.RoomUserAuthRequestVo;
import com.fengwenyi.erwinchatroom.vo.response.RoomUserAuthResponseVo;
import com.fengwenyi.javalib.generate.IdUtils;
import com.github.benmanes.caffeine.cache.Cache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

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
    // 成功 -> 标记用户在指定房间权限验证通过，5分钟内有效，有效次数一次\


    @Autowired
    private IRoomRepository roomRepository;

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private Cache<Object, Object> cache;

    // 用户信息
    @PostMapping("/room-user")
    public ResponseTemplate<?> roomUser(@RequestBody @Validated RoomUserAuthRequestVo requestVo) {
        String rid = requestVo.getRid();
        String uid = requestVo.getUid();
        String password = requestVo.getPassword();

        Optional<RoomEntity> optionalRoom = roomRepository.findById(rid);
        if (!optionalRoom.isPresent()) {
            return ResponseTemplate.fail("房间ID不正确");
        }

        Optional<UserEntity> optionalUser = userRepository.findById(uid);
        if (!optionalUser.isPresent()) {
            return ResponseTemplate.fail("用户ID不正确");
        }

        RoomEntity roomEntity = optionalRoom.get();
        if (!roomEntity.getNeedPassword()) {
            return ResponseTemplate.fail("该房间不需要密码");
        }

        if (roomEntity.getCreateUserUid().equals(rid)) {
            return ResponseTemplate.fail("该房间创建者不需要密码");
        }

        boolean check = PasswordUtils.check(password, roomEntity.getPassword());
        if (!check) {
            return ResponseTemplate.fail("密码不正确");
        }

        String token = IdUtils.genId();

        cache.put(CacheKeyUtils.genRoomUserAuthKey(rid, uid), token);

        return ResponseTemplate.success(new RoomUserAuthResponseVo().setToken(token));
    }

}
