package org.springframework.boot.container.core.service;

import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisPool;

import java.util.Date;

/**
 * @author eric E-mail:
 * @version 创建时间：2018/6/6 下午5:02
 * Cache
 */
public interface CacheService{
    /***
     * 获取JedisPool
     * @return
     */
    JedisPool getJedisPool();
}
