package org.springframework.boot.container.core.service.impl;

import org.springframework.boot.container.core.service.UniqueSequenceServiceService;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * @author eric E-mail:
 * @version 创建时间：2018/3/29 下午3:57
 * UniqueSequenceService
 */
@Service
public class UniqueSequenceServiceServiceImpl extends BaseServiceImpl implements UniqueSequenceServiceService{

    @Override
    public String getUUID(){
        return UUID.randomUUID().toString().replace("-", "");
    }
}
