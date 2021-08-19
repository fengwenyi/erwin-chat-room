package com.fengwenyi.erwinchatroom.controller;

import com.fengwenyi.erwinchatroom.domain.UserModel;
import com.fengwenyi.erwinchatroom.service.IUserService;
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

/**
 * @author <a href="https://www.fengwenyi.com">Erwin Feng</a>
 * @since 2021-08-13
 */
@Controller
@Slf4j
public class ViewController {

    @Autowired
    private IUserService userService;

    @GetMapping("/")
    public String index(Model model, HttpSession session, HttpServletResponse response) {
        session.setMaxInactiveInterval(-1);
        String sessionId = session.getId();

        userService.init(sessionId);

        /*UserModel userModel = UserUtils.queryByUid(uid);
        if (Objects.nonNull(userModel)) {
            model.addAttribute("nickname", userModel.getNickname());
        }*/

        // 解决浏览器后退不能自动刷新问题
        response.setHeader("Pragma","no-cache");
        response.setHeader("Cache-Control","no-cache");
        response.setDateHeader("Expires", 0);
        response.setHeader("Cache-Control", "no-store");

        return "index";
    }

    @GetMapping("/chat-room/{uid}")
    public String chatRoom(@PathVariable String uid, Model model, HttpSession session) {
        if (!session.getId().equals(uid)
                || Objects.isNull(UserUtils.queryByUid(uid))) {
            model.addAttribute("error", true);
            model.addAttribute("msg", "非法进入聊天室");
        } else {
            model.addAttribute("uid", uid);
        }
        return "chat-room";
    }
}
