package com.fengwenyi.erwinchatroom.service.impl;

import com.fengwenyi.api.result.ResultTemplate;
import com.fengwenyi.erwinchatroom.entity.UserEntity;
import com.fengwenyi.erwinchatroom.repository.IUserRepository;
import com.fengwenyi.erwinchatroom.service.IChatService;
import com.fengwenyi.erwinchatroom.service.IMsgService;
import com.fengwenyi.erwinchatroom.vo.request.ChatMessageRequestVo;
import com.fengwenyi.erwinchatroom.vo.response.ChatMessageResponseVo;
import com.fengwenyi.erwinchatroom.vo.response.ContentResponseVo;
import com.fengwenyi.erwinchatroom.vo.response.UserResponseVo;
import com.fengwenyi.javalib.convert.DateTimeUtils;
import com.fengwenyi.javalib.convert.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * @author <a href="https://www.fengwenyi.com">Erwin Feng</a>
 * @since 2021-08-25
 */
@Service
@Slf4j
public class ChatServiceImpl implements IChatService {

    @Autowired
    private IMsgService msgService;

    @Autowired
    private IUserRepository userRepository;

    @Override
    public ResultTemplate<Void> room(ChatMessageRequestVo requestVo) {

        log.debug("请求参数={}", JsonUtils.convertString(requestVo));

        Optional<UserEntity> optionalUser = userRepository.findById(requestVo.getUid());

        if (!optionalUser.isPresent()) {
            log.debug("用户查询失败，uid={}", requestVo.getUid());
            return ResultTemplate.fail("用户查询失败");
        }

        ChatMessageResponseVo chatMessageResponseVo = new ChatMessageResponseVo();

        ContentResponseVo contentResponseVo = new ContentResponseVo();
        contentResponseVo.setContentType(requestVo.getMessage().getContentType());
        contentResponseVo.setContent(requestVo.getMessage().getContent());

        chatMessageResponseVo.setMessage(contentResponseVo);

        UserEntity userEntity = optionalUser.get();

        chatMessageResponseVo.setSender(
                new UserResponseVo()
                        .setUid(userEntity.getUid())
                        .setNickname(userEntity.getNickname())
                        .setAvatarBgColor(userEntity.getAvatarBgColor()));
        chatMessageResponseVo.setTimestamp(System.currentTimeMillis() / 1000);
        chatMessageResponseVo.setTimeStr(DateTimeUtils.format(LocalDateTime.now(), "HH:mm"));

        ResultTemplate<?> result = ResultTemplate.success(chatMessageResponseVo);

        log.debug("发送的消息：{}", JsonUtils.convertString(result));

        msgService.sendToRoom(requestVo.getRid(), result);

        return ResultTemplate.success();
    }
}
