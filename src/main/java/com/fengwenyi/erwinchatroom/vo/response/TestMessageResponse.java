package com.fengwenyi.erwinchatroom.vo.response;

import org.springframework.messaging.Message;
import org.springframework.messaging.core.MessagePostProcessor;
import org.springframework.messaging.support.GenericMessage;

/**
 * @author <a href="https://www.fengwenyi.com">Erwin Feng</a>
 * @since 2021-08-24
 */
public class TestMessageResponse implements MessagePostProcessor {
    @Override
    public Message<?> postProcessMessage(Message<?> message) {
        return new GenericMessage("tsetsadksap[dfkdsfksdf dsfsfsd");
    }
}
