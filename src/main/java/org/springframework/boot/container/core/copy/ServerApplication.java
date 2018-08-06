package org.springframework.boot.container.core;

import org.springframework.boot.container.core.utils.BaseUtils;
import org.springframework.boot.container.core.utils.HttpClientUtil;

import java.util.ArrayList;
import java.util.List;

/***
 * @author 王强 Email : 
 * @version 创建时间：2018/7/10
 * ServerApplication
 */
public abstract class ServerApplication extends BaseApplication{
    /***
     * 下划线
     */
    private final static String UNDERLINE = "_";

    /***
     * 命令前缀
     */
    private final static String PREFIX = "--";

    /**
     * 数据库名称
     */
    private final static String DB_NAME = "micro.server.db-name";

    /***
     * Equal sign 等于号
     */
    private final static String EQUAL_SING = "=";

    /**
     * 根据服务名称获取数据库名称
     * @param serverName 服务名称
     * @return
     */
    public String getDbName(String serverName){
        return BaseUtils.StringUtilsSon.addMarkToString(serverName, UNDERLINE);
    }

    public String getDbName(){
        return this.getDbName(this.getServerName());
    }

    /***
     * 获取db名称配置
     * @return
     */
    private String getDbNameConfig(){
        return PREFIX + DB_NAME + EQUAL_SING + this.getDbName();
    }

    @Override
    public void run(Object source, String... args){
        List<String> argList = new ArrayList<>();
        for(String arg : args){
            argList.add(arg);
        }
        argList.add(this.getDbNameConfig());
        super.run(source, argList.toArray(new String[argList.size()]));
    }
}
