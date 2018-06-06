package org.springframework.boot.container.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.container.core.mysql.mapper.db2.Db2Mapper;
import org.springframework.boot.container.core.service.Db2Service;
import org.springframework.stereotype.Service;

/***
 * @author 王强 Email : 
 * @version 创建时间：2018/4/18
 * Db2ServiceImpl
 */
@Service
public class Db2ServiceImpl extends DaoMysqlServiceImpl implements Db2Service{
    @Autowired
    private Db2Mapper db2Mapper;

    public void setBaseMapper(){
        this.setBaseMapperGetService(db2Mapper);
    }
}
