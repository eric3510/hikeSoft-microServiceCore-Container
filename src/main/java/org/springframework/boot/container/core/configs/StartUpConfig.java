package org.springframework.boot.container.core.configs;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/***
 * @author 王强 Email : 
 * @version 创建时间：2018/6/6
 * StartUpConfig
 */
@Data
@Component
@ConfigurationProperties(prefix = "start-up-config")
public class StartUpConfig{
    private Boolean mysql;

    private Boolean redis;
}
