package org.springframework.boot.container.core.configs;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.container.core.SpringContext;
import org.springframework.stereotype.Component;

/***
 * @author 王强 Email : 
 * @version 创建时间：2018/8/3
 * ServerConfig
 */
@Data
@Component
public class ServerConfig{
    @Value("${spring.application.name}")
    private String springApplicationName;

    private static ServerConfig serverConfig;

    /***
     * 单例模式,防止SpringContext.getBean()多次执行
     * @return
     */
    public static synchronized ServerConfig getConfig(){
        if(ServerConfig.serverConfig == null){
            serverConfig = SpringContext.getBean(ServerConfig.class);
        }
        return ServerConfig.serverConfig;
    }
}
