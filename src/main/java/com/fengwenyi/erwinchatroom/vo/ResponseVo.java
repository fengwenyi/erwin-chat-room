package com.fengwenyi.demospringbootwebsocket.vo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author <a href="https://www.fengwenyi.com">Erwin Feng</a>
 * @since 2021-08-12
 */
@Data
@Accessors(chain = true)
public class ResponseVo {

    private Integer msgType;

    private String username;

    private String message;

}
