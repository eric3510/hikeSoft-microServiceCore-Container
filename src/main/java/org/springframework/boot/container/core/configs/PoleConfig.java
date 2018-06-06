package org.springframework.boot.container.core.configs;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/***
 * @author 王强 Email : 
 * @version 创建时间：2018/4/4
 * PoleConfig
 */
@Data
@Component
@ConfigurationProperties(prefix="pole-config")
public class PoleConfig{
    /***
     * 需要解析的log的路径
     */
    private String logPath;

    /***
     * 数据库名称
     */
    private String dbName;

    /***
     * 项目路径
     */
    private String basedir;

    /***
     * 获取所有路径
     * @return
     */
    public String[] getLogPaths(){
        return this.getLogPath().split(",");
    }
}
