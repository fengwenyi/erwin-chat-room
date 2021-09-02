package com.fengwenyi.erwinchatroom.controller;

import com.fengwenyi.api.result.ResultTemplate;
import com.fengwenyi.erwinchatroom.entity.RoomEntity;
import com.fengwenyi.erwinchatroom.entity.RoomInviteEntity;
import com.fengwenyi.erwinchatroom.entity.UserEntity;
import com.fengwenyi.erwinchatroom.repository.IRoomInviteRepository;
import com.fengwenyi.erwinchatroom.repository.IRoomRepository;
import com.fengwenyi.erwinchatroom.repository.IUserRepository;
import com.fengwenyi.erwinchatroom.utils.CacheKeyUtils;
import com.fengwenyi.erwinchatroom.utils.UserUtils;
import com.fengwenyi.javalib.convert.DateTimeUtils;
import com.fengwenyi.javalib.generate.IdUtils;
import com.github.benmanes.caffeine.cache.Cache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * @author <a href="https://www.fengwenyi.com">Erwin Feng</a>
 * @since 2021-08-13
 */
@Controller
@Slf4j
public class ViewController {

    @Autowired
    private IRoomRepository roomRepository;

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private Cache<Object, Object> cache;

    @GetMapping("/")
    public String index(HttpSession session, HttpServletResponse response) {
        session.setMaxInactiveInterval(-1);

        // 解决浏览器后退不能自动刷新问题
        response.setHeader("Pragma","no-cache");
        response.setHeader("Cache-Control","no-cache");
        response.setDateHeader("Expires", 0);
        response.setHeader("Cache-Control", "no-store");

        return "index";
    }

    // rid
    // password
    @GetMapping("/chat/{rid}/{uid}")
    public String chat(@PathVariable String rid, @PathVariable String uid, String token, Model model) {

        Optional<RoomEntity> optionalRoom = roomRepository.findById(rid);
        if (optionalRoom.isPresent()) {
            RoomEntity roomEntity = optionalRoom.get();

            model.addAttribute("rid", rid);
            model.addAttribute("roomName", roomEntity.getName());
            model.addAttribute("needPassword", roomEntity.getNeedPassword());

            if (roomEntity.getNeedPassword()
                    && !uid.equals(roomEntity.getCreateUserUid())
                    && !StringUtils.hasText(token)) {
                // 房间需要密码
                // 该用户不是房间创建者

                // => 需要校验

                if (!StringUtils.hasText(token)) {
                    // token为空
                    model.addAttribute("error", true);
                    model.addAttribute("msg", "非法进入聊天室");
                } else {

                    String authToken = (String) cache.getIfPresent((CacheKeyUtils.genRoomUserAuthKey(rid, uid)));

                    if (!StringUtils.hasText(authToken)) {
                        model.addAttribute("error", true);
                        model.addAttribute("msg", "非法进入聊天室");
                    } else {
                        if (!token.equals(authToken)) {
                            model.addAttribute("error", true);
                            model.addAttribute("msg", "非法进入聊天室");
                        }
                    }
                }
            }
        } else {
            model.addAttribute("error", true);
            model.addAttribute("msg", "非法进入聊天室");
        }

        return "chat";
    }

    // 邀请
    @GetMapping("/invite")
    public String invite(String rid, String inviteUid, Model model) {
        Optional<RoomEntity> optionalRoom = roomRepository.findById(rid);
        model.addAttribute("rid", rid);
        if (optionalRoom.isPresent()) {
            RoomEntity roomEntity = optionalRoom.get();
            model.addAttribute("roomName", roomEntity.getName());
            if (roomEntity.getNeedPassword()) {
                String token = IdUtils.genId();
                model.addAttribute("token", token);
            }
        }
        Optional<UserEntity> optionalUser = userRepository.findById(inviteUid);
        if (optionalUser.isPresent()) {
            UserEntity userEntity = optionalUser.get();
            model.addAttribute("userNickname", userEntity.getNickname());
        }
        return "invite";
    }
}
