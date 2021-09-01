package com.fengwenyi.erwinchatroom.controller;

import com.fengwenyi.api.result.PageRequest;
import com.fengwenyi.api.result.PageTemplate;
import com.fengwenyi.api.result.ResultTemplate;
import com.fengwenyi.erwinchatroom.entity.RoomInviteEntity;
import com.fengwenyi.erwinchatroom.repository.IRoomInviteRepository;
import com.fengwenyi.erwinchatroom.service.IRoomService;
import com.fengwenyi.erwinchatroom.vo.request.RoomRequestVo;
import com.fengwenyi.erwinchatroom.vo.response.RoomResponseVo;
import com.fengwenyi.erwinchatroom.vo.response.UserResponseVo;
import com.fengwenyi.javalib.convert.DateTimeUtils;
import com.fengwenyi.javalib.generate.IdUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
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

    @Autowired
    private IRoomInviteRepository roomInviteRepository;

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

    @GetMapping("{rid}/invite")
    public ResultTemplate<?> invite(@PathVariable String rid, String inviteUid) {
        LocalDateTime inviteTime = LocalDateTime.now();
        String inviteId;

        RoomInviteEntity queryEntity = new RoomInviteEntity().setRid(rid).setInviteUid(inviteUid);

        Example<RoomInviteEntity> example = Example.of(queryEntity);

        List<RoomInviteEntity> list = roomInviteRepository.findAll(example);

        RoomInviteEntity entity;
        if (CollectionUtils.isEmpty(list)) {
            inviteId = IdUtils.genId();
            entity = new RoomInviteEntity()
                    .setRid(rid)
                    .setInviteUid(inviteUid)
                    .setId(inviteId)
                    .setInviteTime(inviteTime);
            roomInviteRepository.save(entity);
        } else {
            entity = list.get(0);
            inviteId = entity.getId();
        }

        String inviteUrl = "http://localhost:8080" +
                "/invite?" +
                "id=" + inviteId +
                "&rid=" + rid +
                "&inviteUid" + inviteUid +
                "&it=" + DateTimeUtils.toMillisecond(inviteTime) +
                "&ct=" + System.currentTimeMillis();

        return ResultTemplate.success(inviteUrl);
    }
}
