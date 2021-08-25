package com.fengwenyi.erwinchatroom.controller;

import com.fengwenyi.erwinchatroom.entity.RoomEntity;
import com.fengwenyi.erwinchatroom.repository.IRoomRepository;
import com.fengwenyi.erwinchatroom.utils.UserUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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
    @GetMapping("/chat/{rid}")
    public String chat(@PathVariable String rid, Model model) {

        Optional<RoomEntity> optionalRoom = roomRepository.findById(rid);
        if (optionalRoom.isPresent()) {
            RoomEntity roomEntity = optionalRoom.get();
            model.addAttribute("rid", rid);
            model.addAttribute("roomName", roomEntity.getName());
        } else {
            model.addAttribute("error", true);
            model.addAttribute("msg", "非法进入聊天室");
        }

        return "chat";
    }
}
