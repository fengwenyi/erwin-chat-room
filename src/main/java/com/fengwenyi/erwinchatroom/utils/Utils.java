package com.fengwenyi.erwinchatroom.utils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Objects;

/**
 * @author <a href="https://www.fengwenyi.com">Erwin Feng</a>
 * @since 2021-10-27
 */
public class Utils {

    /**
     * 描述过去
     * @param time 时间
     * @return 自然语言描述过去的时间
     */
    public static String descPast(LocalDateTime time) {

        if (Objects.isNull(time)) {
            return "";
        }

        long nowTime = System.currentTimeMillis(); // 获取当前时间的毫秒数

        String msg = "";

        long dateDiff = nowTime - time.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();

        if (dateDiff >= 0) {
            long dateTemp1 = dateDiff  / 1000; // 秒
            long dateTemp2 = dateTemp1 / 60;   // 分钟
            long dateTemp3 = dateTemp2 / 60;   // 小时
            long dateTemp4 = dateTemp3 / 24;   // 天数
            long dateTemp5 = dateTemp4 / 30;   // 月数
            long dateTemp6 = dateTemp5 / 12;   // 年数
            if (dateTemp6 > 0)
                msg = dateTemp6 + "年前";
            else if (dateTemp5 > 0)
                msg = dateTemp5 + "个月前";
            else if (dateTemp4 > 0) {
                if (dateTemp4 == 1) {
                    msg = "昨天";
                } else if (dateTemp4 == 2) {
                    msg = "前天";
                } else {
                    msg = dateTemp4 + "天前";
                }
            }
            else if (dateTemp3 > 0)
                msg = dateTemp3 + "小时前";
            else if (dateTemp2 > 0)
                msg = dateTemp2 + "分钟前";
            else if (dateTemp1 > 0)
                msg = dateTemp1 + "秒前";
            else
                msg = "刚刚";
        }
        return msg;

    }

}
