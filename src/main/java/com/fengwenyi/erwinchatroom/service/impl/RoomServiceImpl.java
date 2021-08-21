package com.fengwenyi.erwinchatroom.service.impl;

import com.fengwenyi.api.result.PageRequest;
import com.fengwenyi.api.result.PageTemplate;
import com.fengwenyi.api.result.ResultTemplate;
import com.fengwenyi.apistarter.utils.Asserts;
import com.fengwenyi.erwinchatroom.entity.RoomEntity;
import com.fengwenyi.erwinchatroom.entity.UserEntity;
import com.fengwenyi.erwinchatroom.repository.IRoomRepository;
import com.fengwenyi.erwinchatroom.repository.IUserRepository;
import com.fengwenyi.erwinchatroom.service.IRoomService;
import com.fengwenyi.erwinchatroom.vo.request.RoomRequestVo;
import com.fengwenyi.erwinchatroom.vo.response.RoomResponseVo;
import com.fengwenyi.javalib.convert.DateTimeUtils;
import com.fengwenyi.javalib.generate.IdUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author <a href="https://www.fengwenyi.com">Erwin Feng</a>
 * @since 2021-08-21
 */
@Service
public class RoomServiceImpl implements IRoomService {

    @Autowired
    private IRoomRepository roomRepository;

    @Autowired
    private IUserRepository userRepository;

    @Override
    public ResultTemplate<RoomResponseVo> create(RoomRequestVo requestVo) {
        UserEntity userEntity = userRepository.findById(requestVo.getCreateUserUid()).orElse(null);
        if (Objects.isNull(userEntity)) {
            Asserts.fail("创建者ID不正确");
        }
        RoomEntity roomEntity = roomRepository.save(
                new RoomEntity()
                        .setRid(IdUtils.genId())
                        .setName(requestVo.getName())
                        .setCreateTime(LocalDateTime.now())
                        .setPassword(requestVo.getPassword())
                        .setNeedPassword(StringUtils.hasText(requestVo.getPassword()))
                        .setCreateTime(LocalDateTime.now())
                        .setUserCount(0));
        RoomResponseVo responseVo = new RoomResponseVo()
                .setRid(roomEntity.getRid())
                .setName(roomEntity.getName())
                .setNeedPassword(roomEntity.getNeedPassword())
                .setCreateUserNickname(userEntity.getNickname())
                .setCreateTimeString(DateTimeUtils.format(roomEntity.getCreateTime(), "HH:mm"))
                ;
        return ResultTemplate.success(responseVo);
    }

    @Override
    public ResultTemplate<PageTemplate<List<RoomResponseVo>>> getPage(PageRequest<?> pageRequest) {
        Long currentPage = pageRequest.getCurrentPage();
        Integer pageSize = pageRequest.getPageSize();
        org.springframework.data.domain.PageRequest
                pageRequest1 = org.springframework.data.domain.PageRequest.of(currentPage.intValue() - 1, pageSize, Sort.by("userCount", "createTime"));
        Page<RoomEntity> pageResult = roomRepository.findAll(pageRequest1);
        List<RoomResponseVo> list = pageResult.toList().stream()
                .map(entity -> {
                    UserEntity userEntity = userRepository.findById(entity.getCreateUserUid()).orElse(null);
                    String createUserNickname = Objects.nonNull(userEntity) ? userEntity.getNickname() : "";
                    return new RoomResponseVo()
                            .setRid(entity.getRid())
                            .setName(entity.getName())
                            .setNeedPassword(entity.getNeedPassword())
                            .setCreateUserNickname(createUserNickname)
                            .setCreateTimeString(DateTimeUtils.format(entity.getCreateTime(), "HH:mm"))
                            .setUserCount(entity.getUserCount())
                            ;
                })
                .collect(Collectors.toList());
        PageTemplate<List<RoomResponseVo>> pageTemplate = new PageTemplate<List<RoomResponseVo>>()
                .setCurrentPage(pageRequest.getCurrentPage() + 1)
                .setPageSize(pageResult.getSize())
                .setTotalPages((long) pageResult.getTotalPages())
                .setTotalRows(pageResult.getTotalElements())
                .setContent(list)
                ;

        return ResultTemplate.success(pageTemplate);
    }
}
