package org.springframework.boot.container.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.container.core.mysql.mapper.db1.Db1Mapper;
import org.springframework.boot.container.core.service.Db1Service;
import org.springframework.stereotype.Service;

/***
 * @author 王强 Email : 
 * @version 创建时间：2018/4/18
 * Db1ServiceImpl
 */
@Service
public class Db1ServiceImpl extends DaoMysqlServiceImpl implements Db1Service{
    @Autowired
    private Db1Mapper db1Mapper;

    public void setBaseMapper(){
        this.setBaseMapperGetService(db1Mapper);
    }
}
