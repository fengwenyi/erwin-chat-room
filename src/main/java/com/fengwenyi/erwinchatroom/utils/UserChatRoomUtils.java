package com.fengwenyi.erwinchatroom.utils;

import com.fengwenyi.erwinchatroom.domain.UserChatRoomModel;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

/**
 * @author <a href="https://www.fengwenyi.com">Erwin Feng</a>
 * @since 2021-08-14
 */
public class UserChatRoomUtils {

    private static final ConcurrentMap<String, UserChatRoomModel> map = new ConcurrentHashMap<>();

    public static void add(UserChatRoomModel model) {
        map.put(model.getUid(), model);
    }

    public static List<UserChatRoomModel> queryAll() {
        return new ArrayList<>(map.values()).stream().sorted(Comparator.comparing(UserChatRoomModel::getTime)).collect(Collectors.toList());
    }

    public static UserChatRoomModel queryByUid(String uid) {
        return map.get(uid);
    }

    public static void deleteByUid(String uid) {
        map.remove(uid);
    }

    public static void updateByUid(UserChatRoomModel model) {
        map.put(model.getUid(), model);
    }
}
