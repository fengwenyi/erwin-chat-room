package com.fengwenyi.erwinchatroom.task;

import com.fengwenyi.erwinchatroom.service.IRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @author <a href="https://www.fengwenyi.com">Erwin Feng</a>
 * @since 2021-09-04
 */
@Component
public class SubTask {

    @Autowired
    private IRoomService roomService;

    @Async
    public void clearRoomTask() {
        roomService.deleteEmpty();
    }

}
