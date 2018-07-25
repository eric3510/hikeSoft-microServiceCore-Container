package org.springframework.boot.container.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.container.core.service.*;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

/**
 * @author 王强
 * @version 创建时间：2017/08/17 12:14
 * BaseServiceImpl
 **/
@Service
@RestController
public class BaseServiceImpl implements BaseService{
    @Autowired
    private DaoMongoService daoMongoService;

    @Autowired
    private UniqueSequenceServiceService uniqueSequenceServiceService;

    @Autowired
    private Db1Service db1Service;

    @Autowired
    private Db2Service db2Service;

    @Autowired
    private CacheService cacheService;

//    @Autowired
//    private ServiceReference serviceReference;

    public Db1Service getDb1Dao(){
        return this.db1Service;
    }

    public Db2Service getDb2Dao(){
        return this.db2Service;
    }

    public DaoMongoService getMongo(){
        return this.daoMongoService;
    }

    public UniqueSequenceServiceService getUniqueSequenceServiceService(){
        return uniqueSequenceServiceService;
    }

    public JedisPool getCache(){
        return cacheService.getJedisPool();
    }

//    public ServiceReference getServiceReference(){
//        return serviceReference;
//    }
}
