package com.fengwenyi.erwinchatroom.vo.request;

import lombok.Data;

/**
 * @author <a href="https://www.fengwenyi.com">Erwin Feng</a>
 * @since 2021-08-24
 */
@Data
public class ContentRequestVo {

    // 内容类型
    private Integer contentType;

    // 内容
    private String content;

}
