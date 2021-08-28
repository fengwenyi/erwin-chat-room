package com.fengwenyi.erwinchatroom.utils;

import com.fengwenyi.javalib.encryption.MD5Utils;
import com.fengwenyi.javalib.util.PrintUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.security.NoSuchAlgorithmException;

/**
 * 密码工具类
 * @author <a href="https://www.fengwenyi.com">Erwin Feng</a>
 * @since 2021-08-29
 */
public class PasswordUtils {
    // 日志
    private static final Logger logger = LoggerFactory.getLogger(PasswordUtils.class);

    /**
     * 加密
     * @param userPassword 用户真实密码
     * @return 加密后的密码
     */
    public static String encrypt(String userPassword) {
        try {
            return MD5Utils.md5(userPassword);
        } catch (NoSuchAlgorithmException e) {
            logger.error("密码加密失败：{}", e.getLocalizedMessage(), e);
            return null;
        }
    }

    /**
     * 密码校验
     * @param userPassword 用户密码
     * @param password 数据库存储的用户加密密码
     * @return 校验结果，true：校验成功；false：校验失败
     */
    public static boolean check(String userPassword, String password) {
        String encrypt = encrypt(userPassword);
        if (StringUtils.hasText(encrypt)) {
            return encrypt.equals(password);
        }
        return false;
    }

}
