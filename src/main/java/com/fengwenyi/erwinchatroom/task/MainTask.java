package com.fengwenyi.erwinchatroom.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author <a href="https://www.fengwenyi.com">Erwin Feng</a>
 * @since 2021-09-04
 */
@Component
public class MainTask {

    @Autowired
    private SubTask subTask;

    @Scheduled(cron = "0 0 5 * * ?")
    public void clearRoomTask() {
        subTask.clearRoomTask();
    }

}
