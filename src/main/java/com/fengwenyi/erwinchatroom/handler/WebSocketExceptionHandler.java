package com.fengwenyi.erwinchatroom.handler;

import com.fengwenyi.api.result.IReturnCode;
import com.fengwenyi.api.result.ResultTemplate;
import com.fengwenyi.apistarter.exception.ApiException;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;

/**
 * @author <a href="https://www.fengwenyi.com">Erwin Feng</a>
 * @since 2021-08-29
 */
@RestControllerAdvice
public class WebSocketExceptionHandler {

    @MessageExceptionHandler(value = ApiException.class)
    public ResultTemplate<Void> handleException(ApiException exception) {
        IReturnCode returnCode = exception.getReturnCode();
        String message = exception.getMessage();
        if (Objects.isNull(returnCode)) {
            return ResultTemplate.fail(message);
        }
        if (!StringUtils.hasText(message)) {
            return ResultTemplate.fail(returnCode);
        }
        return ResultTemplate.fail(returnCode, message);
    }

}
