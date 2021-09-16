package com.fengwenyi.erwinchatroom.service.impl;

import com.fengwenyi.api.result.PageRequestVo;
import com.fengwenyi.api.result.PageResponseVo;
import com.fengwenyi.api.result.ResultTemplate;
import com.fengwenyi.apistarter.utils.Asserts;
import com.fengwenyi.erwinchatroom.entity.RoomEntity;
import com.fengwenyi.erwinchatroom.entity.RoomUserEntity;
import com.fengwenyi.erwinchatroom.entity.UserEntity;
import com.fengwenyi.erwinchatroom.repository.IRoomRepository;
import com.fengwenyi.erwinchatroom.repository.IRoomUserRepository;
import com.fengwenyi.erwinchatroom.repository.IUserRepository;
import com.fengwenyi.erwinchatroom.service.IRoomService;
import com.fengwenyi.erwinchatroom.utils.PasswordUtils;
import com.fengwenyi.erwinchatroom.vo.request.RoomRequestVo;
import com.fengwenyi.erwinchatroom.vo.response.RoomResponseVo;
import com.fengwenyi.erwinchatroom.vo.response.UserResponseVo;
import com.fengwenyi.javalib.convert.DateTimeUtils;
import com.fengwenyi.javalib.generate.IdUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author <a href="https://www.fengwenyi.com">Erwin Feng</a>
 * @since 2021-08-21
 */
@Service
@Slf4j
public class RoomServiceImpl implements IRoomService {

    @Autowired
    private IRoomRepository roomRepository;

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IRoomUserRepository roomUserRepository;

    @Override
    public ResultTemplate<RoomResponseVo> create(RoomRequestVo requestVo) {
        Optional<UserEntity> optionalUser = userRepository.findById(requestVo.getCreateUserUid());
        if (!optionalUser.isPresent()) {
            Asserts.fail("创建者ID不正确");
        }
        UserEntity userEntity = optionalUser.get();
        if (!StringUtils.hasText(userEntity.getNickname())) {
            Asserts.fail("请先取一个昵称");
        }
        RoomEntity roomEntity = roomRepository.save(
                new RoomEntity()
                        .setRid(IdUtils.genId())
                        .setName(requestVo.getName())
                        .setCreateTime(LocalDateTime.now())
                        .setPassword(StringUtils.hasText(requestVo.getPassword()) ? PasswordUtils.encrypt(requestVo.getPassword()) : "")
                        .setNeedPassword(StringUtils.hasText(requestVo.getPassword()))
                        .setUserCount(0)
                        .setCreateUserUid(userEntity.getUid()));
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
    public ResultTemplate<PageResponseVo<List<RoomResponseVo>>> getPage(PageRequestVo<?> pageRequestVo) {
        Long currentPage = pageRequestVo.getCurrentPage();
        Integer pageSize = pageRequestVo.getPageSize();

        List<Sort.Order> orders = new ArrayList<>();

        orders.add(Sort.Order.asc("needPassword"));
        orders.add(Sort.Order.desc("userCount"));
        orders.add(Sort.Order.asc("createTime"));

        PageRequest pageRequest = PageRequest.of(currentPage.intValue() - 1, pageSize, Sort.by(orders));
        Page<RoomEntity> pageResult = roomRepository.findAll(pageRequest);
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
        PageResponseVo<List<RoomResponseVo>> pageResponseVo = new PageResponseVo<List<RoomResponseVo>>()
                .setCurrentPage((long) pageResult.getNumber() + 1)
                .setPageSize(pageResult.getSize())
                .setTotalPages((long) pageResult.getTotalPages())
                .setTotalRows(pageResult.getTotalElements())
                .setContent(list)
                ;

        return ResultTemplate.success(pageResponseVo);
    }

    @Override
    public ResultTemplate<RoomResponseVo> get(String rid) {
//        log.debug("rid={}", rid);
        Optional<RoomEntity> optionalRoom = roomRepository.findById(rid);
        if (!optionalRoom.isPresent()) {
            return ResultTemplate.fail("房间ID不正确");
        }

        RoomEntity roomEntity = optionalRoom.get();
        Optional<UserEntity> optionalUser = userRepository.findById(roomEntity.getCreateUserUid());
        if (!optionalUser.isPresent()) {
            return ResultTemplate.fail("房间创建者ID异常");
        }
        UserEntity userEntity = optionalUser.get();
        RoomResponseVo responseVo = new RoomResponseVo()
                .setRid(roomEntity.getRid())
                .setName(roomEntity.getName())
                .setNeedPassword(roomEntity.getNeedPassword())
                .setCreateUserUid(userEntity.getUid())
                .setCreateUserNickname(userEntity.getNickname())
                .setCreateTimeString(DateTimeUtils.format(roomEntity.getCreateTime(), "HH:mm"))
                ;
        return ResultTemplate.success(responseVo);
    }

    @Override
    public ResultTemplate<String> getUserCount(String rid) {
        Optional<RoomEntity> optionalRoom = roomRepository.findById(rid);
        if (optionalRoom.isPresent()) {
            Integer userCount = optionalRoom.get().getUserCount();
            return ResultTemplate.success(String.valueOf(userCount));
        }
        return ResultTemplate.success(String.valueOf(0));
    }

    @Override
    public ResultTemplate<List<UserResponseVo>> getUserList(String rid) {
        RoomUserEntity roomUserEntity = new RoomUserEntity().setRid(rid);
        Example<RoomUserEntity> example = Example.of(roomUserEntity);
        List<RoomUserEntity> roomUserEntities = roomUserRepository.findAll(example, Sort.by("entryTime").ascending());
        List<UserResponseVo> responseVoList = roomUserEntities.stream().map(ruEntity -> {
            String uid = ruEntity.getUid();
            Optional<UserEntity> optionalUser = userRepository.findById(uid);
            if (optionalUser.isPresent()) {
                UserEntity userEntity = optionalUser.get();
                return new UserResponseVo()
                        .setUid(uid)
                        .setNickname(userEntity.getNickname())
                        .setAvatarBgColor(userEntity.getAvatarBgColor())
                        .setTimeStr(DateTimeUtils.format(ruEntity.getEntryTime(), "HH:mm:ss"));
            }
            return null;
        }).filter(Objects::nonNull).collect(Collectors.toList());
        return ResultTemplate.success(responseVoList);
    }

    @Override
    public void deleteEmpty() {
        // 删除空房间
        RoomEntity roomEntity = new RoomEntity()
                .setUserCount(0)
                ;
        Example<RoomEntity> example = Example.of(roomEntity);
        List<RoomEntity> list = roomRepository.findAll(example);
        roomRepository.deleteAll(list);
    }
}
