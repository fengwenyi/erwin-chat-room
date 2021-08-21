package com.fengwenyi.erwinchatroom.controller;

import com.fengwenyi.api.result.PageRequest;
import com.fengwenyi.api.result.PageTemplate;
import com.fengwenyi.api.result.ResultTemplate;
import com.fengwenyi.erwinchatroom.service.IRoomService;
import com.fengwenyi.erwinchatroom.vo.request.RoomRequestVo;
import com.fengwenyi.erwinchatroom.vo.response.RoomResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
