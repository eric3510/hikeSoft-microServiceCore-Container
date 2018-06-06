package org.springframework.boot.container.core.service;

/**
 * @author eric E-mail:
 * @version 创建时间：2018/3/29 下午3:57
 * UniqueSequenceService
 */
public interface UniqueSequenceServiceService extends BaseService{
    /***
     * 获取一个UUID
     * @return
     */
    String getUUID();
}
