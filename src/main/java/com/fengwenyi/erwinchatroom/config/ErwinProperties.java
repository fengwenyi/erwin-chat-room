package com.fengwenyi.erwinchatroom.config;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author <a href="https://www.fengwenyi.com">Erwin Feng</a>
 * @since 2021-10-25
 */
@Getter
@Setter
@Configuration
@ConfigurationProperties("erwin")
public class ErwinProperties {

    private String domain;

    private App app;

    @Data
    public static class App {
        private String name;
        private String version;
    }

}
