package org.springframework.boot.container.core.configs;

import lombok.Data;
import org.springframework.boot.container.core.constant.Constant;
import org.springframework.boot.container.core.utils.BaseUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.io.Serializable;

/***
 * @author 王强 Email : 
 * @version 创建时间：2018/6/6
 * JedisConfig
 */
@Data
@Component
@ConfigurationProperties(prefix = "redis-config")
public class JedisConfig{
    /***
     * 非切片连接池
     */
    private JedisPool jedisPool;

    /***
     * Redis服务器IP
     */
    private String host;

    /***
     * Redis的端口号
     */
    private int port;

    /***
     * 访问密码
     */
    private String auth;

    /***
     * 如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。
     */
    private int maxActive;

    /***
     * 控制一个pool最多有多少个状态为idle(空闲的)的jedis实例，默认值也是8。
     */
    private int maxIdle;

    /***
     * 等待可用连接的最大时间，单位毫秒，默认值为-1，表示永不超时。如果超过等待时间，则直接抛出JedisConnectionException；
     */
    private int maxWait;

    /***
     * 过期时间
     */
    private int timeOut;

    /***
     * 在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
     */
    private boolean testOnBorrow;

    public void setConfig(){
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(maxActive);
        config.setMaxIdle(maxIdle);
        config.setMaxWaitMillis(maxWait);
        config.setTestOnBorrow(testOnBorrow);
        if(BaseUtils.isBlank(auth)){
            this.jedisPool = new JedisPool(config, host, port, maxWait);
        }else{
            this.jedisPool = new JedisPool(config, host, port, maxWait, auth);
        }
    }

    public JedisPool getJedisPool(){
        return this.jedisPool;
    }
}
