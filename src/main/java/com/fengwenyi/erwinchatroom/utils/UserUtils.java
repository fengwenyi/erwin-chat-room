package com.fengwenyi.erwinchatroom.utils;

import com.fengwenyi.erwinchatroom.domain.UserModel;
import com.fengwenyi.erwinchatroom.enums.UserStatusEnum;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

/**
 * @author <a href="https://www.fengwenyi.com">Erwin Feng</a>
 * @since 2021-08-13
 */
public class UserUtils {

    private static final ConcurrentMap<String, UserModel> map = new ConcurrentHashMap<>();

    public static void add(UserModel model) {
        map.put(model.getUid(), model);
    }

    public static List<UserModel> queryAll() {
        return new ArrayList<>(map.values());
    }

    public static UserModel queryByUid(String uid) {
        return map.get(uid);
    }

    public static void deleteByUid(String uid) {
        map.remove(uid);
    }

    public static void updateStatusByUid(String uid, UserStatusEnum userStatusEnum) {
        UserModel userModel = queryByUid(uid);
        userModel.setStatus(userStatusEnum).setUpdateTime(LocalDateTime.now());
        updateByUid(userModel);
    }

    public static void updateByUid(UserModel model) {
        map.put(model.getUid(), model);
    }

}
