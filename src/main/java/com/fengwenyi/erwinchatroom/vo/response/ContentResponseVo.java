package com.fengwenyi.erwinchatroom.vo.response;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author <a href="https://www.fengwenyi.com">Erwin Feng</a>
 * @since 2021-08-24
 */
@Data
@Accessors(chain = true)
public class ContentResponseVo {

    private Integer contentType;

    private String content;

}
