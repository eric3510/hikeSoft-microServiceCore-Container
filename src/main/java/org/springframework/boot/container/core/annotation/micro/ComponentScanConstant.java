package org.springframework.boot.container.core.annotation.micro;

/***
 * @author 王强 Email : 
 * @version 创建时间：2018/8/23
 * ComponentScanConstant
 */
public class ComponentScanConstant{
    /***
     * 架构
     */
    protected static final String CONTAINER_CORE = "org.springframework.boot.container.core";

    /***
     * microservice.server
     */
    protected static final String MICROSERVICE_SERVER = "microservice.server";

    /***
     * 天行健业务服务
     */
    protected static final String TXJ_SERVER = "txj.server";

    /***
     * 任务调度基础路径
     */
    protected static final String TASK_SCHEDULING_BASE = "org.springframework.task.scheduling.";

    /***
     * 计时器服务
     */
    protected static final String CLOCK_SERVER = TASK_SCHEDULING_BASE + "clock";

    /***
     * 任务调度系统配置
     */
    protected static final String TASK_SCHEDULING_CONFIG_SERVER = TASK_SCHEDULING_BASE + "actuator";

    /***
     * 任务调度系统:存储服务
     */
    protected static final String STORAGE_SERVER = TASK_SCHEDULING_BASE + "storage";

    /***
     * 发布任务
     */
    protected static final String TASK_RELEASE_SERVER = TASK_SCHEDULING_BASE + "release";

    /***
     * 任务调度初始化
     */
    protected static final String INIT = TASK_SCHEDULING_BASE + "init";
}
