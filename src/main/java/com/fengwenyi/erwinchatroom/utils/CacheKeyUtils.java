package com.fengwenyi.erwinchatroom.utils;

/**
 * 缓存key工具类
 * @author <a href="https://www.fengwenyi.com">Erwin Feng</a>
 * @since 2021-08-29
 */
public class CacheKeyUtils {

    /**
     * 生成房间用户认证缓存key
     * @param rid 房间ID
     * @param uid 用户ID
     * @return 缓存key
     */
    public static String genRoomUserAuthKey(String rid, String uid) {
        return String.format("room-user-auth-%s-%s", rid, uid);
    }

}
