package com.fengwenyi.erwinchatroom.controller;

import com.fengwenyi.api.result.PageRequest;
import com.fengwenyi.api.result.PageTemplate;
import com.fengwenyi.api.result.ResultTemplate;
import com.fengwenyi.erwinchatroom.service.IRoomService;
import com.fengwenyi.erwinchatroom.vo.request.RoomRequestVo;
import com.fengwenyi.erwinchatroom.vo.response.RoomResponseVo;
import com.fengwenyi.erwinchatroom.vo.response.UserResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author <a href="https://www.fengwenyi.com">Erwin Feng</a>
 * @since 2021-08-19
 */
@RestController
@RequestMapping("/room")
public class RoomController {

    @Autowired
    private IRoomService roomService;

    @PostMapping("/create")
    public ResultTemplate<RoomResponseVo> create(@RequestBody @Validated RoomRequestVo requestVo) {
        return roomService.create(requestVo);
    }

    @PostMapping("/getPage")
    public ResultTemplate<PageTemplate<List<RoomResponseVo>>> getPage(@RequestBody PageRequest<?> pageRequest) {
        return roomService.getPage(pageRequest);
    }

    @GetMapping("/{rid}")
    public ResultTemplate<RoomResponseVo> get(@PathVariable String rid) {
        return roomService.get(rid);
    }

    @GetMapping("/{rid}/user-count")
    public ResultTemplate<String> getUserCount(@PathVariable String rid) {
        return roomService.getUserCount(rid);
    }

    @GetMapping("/{rid}/users")
    public ResultTemplate<List<UserResponseVo>> getUserList(@PathVariable String rid) {
        return roomService.getUserList(rid);
    }
}
