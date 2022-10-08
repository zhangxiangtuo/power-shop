package com.zxt.properties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.io.Serializable;

/**
 * @author zhangxiangtuo
 * @date 2022/10/8 19:25
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Configuration
@ConfigurationProperties("config.info")
public class ConfigInfoProperties implements Serializable {
    private String name;
    private String group;
    private Integer version;
}
