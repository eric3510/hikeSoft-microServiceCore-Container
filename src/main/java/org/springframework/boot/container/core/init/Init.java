package org.springframework.boot.container.core.init;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.container.core.configs.JedisConfig;
import org.springframework.boot.container.core.configs.StartUpConfig;
import org.springframework.boot.container.core.service.impl.Db1ServiceImpl;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/***
 * @author 王强 Email : 
 * @version 创建时间：2018/4/4
 * Init
 */
@Component
public class Init implements ApplicationRunner{
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private Db1ServiceImpl db1ServiceImpl;

    @Autowired
    private Db2ServiceImpl db2ServiceImpl;

    @Autowired
    private JedisConfig jedisConfig;

    @Autowired
    private StartUpConfig startUpConfig;

    @Override
    public void run(ApplicationArguments args) throws Exception{
        //项目实例初始化

        //装载db操作实例
        if(startUpConfig.getMysql()){
            this.setMapper();
            logger.info("mysql已启动");
        }else{
            logger.info("mysql已关闭");
        }

        //装载redis操作实例
        if(startUpConfig.getRedis()){
            this.jedisConfig.setConfig();
            logger.info("redis已启动");
        }else{
            logger.info("redis已关闭");
        }

        //创建api
        DocApi.createApi(System.getProperty("user.dir"));

        logger.info("服务启动成功");
    }

    private void setMapper(){
        db1ServiceImpl.setBaseMapper();
        db2ServiceImpl.setBaseMapper();
    }

    /***
     * 监控是否有新的需要统计的url添加
     */
    private void timerCreateStatisticsTable(){
    }

    /***
     * 每天凌晨两点开始统计url(只统计最后一天的log)
     */
    @Scheduled(cron = "0 0 2 * * ?")
    private void statisticsUrl(){
    }
}
