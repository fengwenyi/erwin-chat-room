package com.fengwenyi.erwinchatroom.utils;

import com.fengwenyi.erwinchatroom.domain.UserModel;

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


    public static void add(UserModel userModel) {
        map.put(userModel.getId(), userModel);
    }

    public static List<UserModel> queryAll() {
        return new ArrayList<>(map.values());
    }

    public static UserModel queryById(String id) {
        return map.get(id);
    }

    public static void deleteById(String id) {
        map.remove(id);
    }

}
