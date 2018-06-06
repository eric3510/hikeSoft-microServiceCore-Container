package org.springframework.boot.container.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.container.core.configs.JedisConfig;
import org.springframework.stereotype.Service;

import java.util.Date;

import org.springframework.boot.container.core.service.CacheService;
import redis.clients.jedis.JedisPool;

/**
 * @author eric E-mail:
 * @version 创建时间：2018/6/6 下午5:02
 * Cache
 */
@Service
public class CacheServiceImpl implements CacheService{
    @Autowired
    private JedisConfig jedisConfig;

    public JedisPool getJedisPool(){
        return this.jedisConfig.getJedisPool();
    }
}
