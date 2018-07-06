package org.springframework.boot.container.core;

import lombok.Data;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.container.core.utils.BaseUtils;

import java.util.*;

/***
 * @author 王强 Email : 
 * @version 创建时间：2018/7/5
 * BaseApplication
 */
@Data
public abstract class BaseApplication{
    public BaseApplication(){
    }

    public BaseApplication(String port){
        this.port = port;
    }

    /***
     * 端口(默认8080)
     */
    private String port = "8080";

    /**
     * 服务名称
     */
    private final static String SERVER_NAME = "micro.server.name";

    /**
     * 数据库名称
     */
    private final static String DB_NAME = "micro.server.db-name";

    /***
     * 端口
     */
    private final static String SERVER_PORT = "server.port";

    /***
     * 选择启动的参数，当没有传入时默认
     */
    private final static String SPRING_PROFILES_ACTIVE = "spring.profiles.active";

    /***
     * 命令前缀
     */
    private final static String PREFIX = "--";

    /***
     * Equal sign 等于号
     */
    private final static String EQUAL_SING = "=";

    /***
     * 中划线
     */
    private final static String  AMONG_LINE = "-";

    /***
     * 下划线
     */
    private final static String UNDERLINE =  "_";

    /***
     * 本地环境
     */
    private final static String PREFILES_LOCAL = "local";

    /***
     * 测试环境
     */
    private final static String PREFILES_TEST = "test";

    /***
     * 开发环境
     */
    private final static String PREFILES_DEVELOP = "develop";

    /***
     * 线上环境
     */
    private final static String PREFILES_ONLINE = "online";

    /***
     * 获取服务名称
     * @return
     */
    public abstract String getServerName();

    /***
     * 根据服务名称获取微服务名称
     * @return
     */
    public String getMicroServerName(){
        return BaseUtils.StringUtilsSon.addMarkToString(this.getServerName(), AMONG_LINE);
    }

    /**
     * 根据服务名称获取数据库名称
     * @param serverName 服务名称
     * @return
     */
    public String getDbName(String serverName){
        return BaseUtils.StringUtilsSon.addMarkToString(serverName, UNDERLINE);
    }

    /***
     * 启动服务, 命令行参数级别最高(参数样例 --server.port=9999)
     * @param source 启动源
     * @param args 启动参数
     */
    public void run(Object source, String... args){
        TreeMap<String, String> argsMap = new TreeMap<>();

        String[] params = this.getParams();

        //以命令行传进来的参数为主
        for(String param : params){
            paramToMap(param, argsMap);
        }

        for(String param : args){
            paramToMap(param, argsMap);
        }

        //过滤需要处理的启动参数
        this.filter(argsMap);

        Iterator<Map.Entry<String, String>> iterator = argsMap.entrySet().iterator();
        List<String> paramList = new ArrayList<>();
        while(iterator.hasNext()){
            Map.Entry<String, String> mapEntry = iterator.next();
            String key = mapEntry.getKey();
            String value = mapEntry.getValue();
            String param = PREFIX + key + EQUAL_SING + value;
            paramList.add(param);
        }
        SpringApplication.run(source, paramList.toArray(new String[paramList.size()]));
    }

    /***
     * 获取Application中的启动参数数组
     * @return
     */
    private String[] getParams(){
        List<String> startupParamList = new ArrayList<>();
        //装载配置
        startupParamList.add(this.getServerNameConfig());
        startupParamList.add(this.getServerPortConfig());
        startupParamList.add(this.getDbNameConfig());

        return startupParamList.toArray(new String[startupParamList.size()]);
    }

    /***
     * 过滤出需要处理的参数
     * @param argsMap 启动参数集合
     */
    private void filter(TreeMap<String, String> argsMap){
        this.filterProfiles(argsMap);
    }

    /***
     * 处理环境参数(如果为local的话则判定为在本地运行，则测试环境以及开发环境不会请求这个服务实例)
     * @param argsMap argsMap
     *
     */
    private List<String> filterProfiles(TreeMap<String, String> argsMap){
        List<String> list = new ArrayList<>();
        //不为空则说明已有环境配置
        if(argsMap.get(SPRING_PROFILES_ACTIVE) != null){
            return null;
        }
        //如果为空则默认本地环境
        this.putLocalServerName(argsMap);
        argsMap.put(SPRING_PROFILES_ACTIVE, PREFILES_LOCAL);
        return list;
    }

    /***
     * 获取服务名称配置
     * @return
     */
    private String getServerNameConfig(){
        return PREFIX + SERVER_NAME + EQUAL_SING + this.getMicroServerName();
    }

    /***
     * 获取端口配置
     * @return
     */
    private String getServerPortConfig(){
        return PREFIX + SERVER_PORT + EQUAL_SING + this.getPort();
    }

    /***
     * 获取db名称配置
     * @return
     */
    private String getDbNameConfig(){
        return PREFIX + DB_NAME + EQUAL_SING + this.getDbName(this.getServerName());
    }

    public void paramToMap(String param, Map<String, String> paramMap){
        //过滤出spring boot配置
        if(!param.contains(PREFIX)){
            return;
        }
        String[] paramSplit = param.split(EQUAL_SING);
        if(paramSplit.length != 2){
            return;
        }
        paramMap.put(paramSplit[0].replace(PREFIX, ""), paramSplit[1]);
    }

    private void putLocalServerName(TreeMap<String, String> treeMap){
        String microServerName = this.getMicroServerName();
        treeMap.put(SERVER_NAME, treeMap.get(SERVER_NAME).replace(microServerName, microServerName + AMONG_LINE + PREFILES_LOCAL));
    }
}
