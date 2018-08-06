package org.springframework.boot.container.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.container.core.utils.BaseUtils;

import java.util.*;

/***
 * @author 王强 Email : 
 * @version 创建时间：2018/8/1
 * BaseApplication
 */
public abstract class BaseApplication{
    /***
     * 选择启动的参数，当没有传入时默认
     */
    public final static String SPRING_PROFILES_ACTIVE = "spring.profiles.active";

    /***
     * 本地环境
     */
    public final static String PREFILES_LOCAL = "local";

    /***
     * 测试环境
     */
    public final static String PREFILES_TEST = "test";

    /**
     * 服务名称
     */
    private final static String SERVER_NAME = "spring.application.name";

    /**
     * 数据库名称
     */
    private final static String DB_NAME = "db-name";

    /***
     * 中划线
     */
    private final static String AMONG_LINE = "-";

    /***
     * 前缀
     */
    private final static String PREFIX = "--";

    /***
     * Equal sign 等于号
     */
    private final static String EQUAL_SING = "=";



    /***
     * 获取服务名称
     * @return
     */
    public abstract String getServerName();


    public void run(Object source, String... args){
        //判断是否在本地启动, 如果在则修改名称后缀为[-local]
        TreeMap<String, String> argsMap = new TreeMap<>();

        String[] params = this.getParams();

        //以命令行传进来的参数为主
        for(String param : params){
            paramToMap(param, argsMap);
        }

        for(String param : args){
            paramToMap(param, argsMap);
        }
        this.filterProfiles(argsMap);
        List<String> paramList = new ArrayList<>();
        this.setParam(argsMap, paramList);
        SpringApplication.run(source, paramList.toArray(new String[paramList.size()]));
    }

    public void setParam(Map<String, String> argsMap, List<String> paramList){
        Iterator<Map.Entry<String, String>> iterator = argsMap.entrySet().iterator();
        while(iterator.hasNext()){
            Map.Entry<String, String> mapEntry = iterator.next();
            String key = mapEntry.getKey();
            String value = mapEntry.getValue();
            String param = PREFIX + key + EQUAL_SING + value;
            paramList.add(param);
        }
    }

    /***
     * 过滤出spring boot配置并装载到map
     * @param param
     * @param paramMap
     */
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

    /***
     * 获取Application中的启动参数数组
     * @return
     */
    private String[] getParams(){
        List<String> startupParamList = new ArrayList<>();
        //装载配置
        //装载配置
        startupParamList.add(this.getServerNameConfig());
        startupParamList.add(this.getDbNameConfig());
        return startupParamList.toArray(new String[startupParamList.size()]);
    }

    /***
     * 获取服务名称配置
     * @return
     */
    private String getServerNameConfig(){
        return PREFIX + SERVER_NAME + EQUAL_SING + this.getMicroServerName();
    }

    /***
     * 处理环境参数(如果为local的话则判定为在本地运行，则测试环境以及开发环境不会请求这个服务实例)
     * @param argsMap argsMap
     */
    private void filterProfiles(TreeMap<String, String> argsMap){
        List<String> list = new ArrayList<>();
        //不为空则说明已有环境配置
        if(argsMap.get(SPRING_PROFILES_ACTIVE) != null){
            return;
        }
        //如果为空则默认本地环境
        this.putLocalServerName(argsMap);
        argsMap.put(SPRING_PROFILES_ACTIVE, PREFILES_LOCAL);
    }

    /***
     * 生成本地启动的服务的名称
     * @param treeMap reeMap
     */
    private void putLocalServerName(TreeMap<String, String> treeMap){
        String microServerName = this.getMicroServerName();
        treeMap.put(SERVER_NAME, treeMap.get(SERVER_NAME).replace(microServerName, microServerName + AMONG_LINE + PREFILES_LOCAL));
    }

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
        return BaseUtils.StringUtilsSon.addMarkToString(serverName, "_");
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
}
