package com.fengwenyi.erwinchatroom.config;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * @author <a href="https://www.fengwenyi.com">Erwin Feng</a>
 * @since 2021-08-29
 */
@Configuration
@EnableCaching
public class CacheConfig {

    @Bean
    public Cache<Object, Object> cache() {
        return Caffeine.newBuilder()
                .maximumSize(1024)
                .expireAfterWrite(5, TimeUnit.MINUTES)
                .build();
    }

}
