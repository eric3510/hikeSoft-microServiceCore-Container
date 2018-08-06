package org.springframework.boot.container.core.service.impl;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.container.core.pojo.AsyncDTO;
import org.springframework.boot.container.core.service.MessageService;
import org.springframework.boot.container.core.utils.BaseUtils;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.util.Map;

/***
 * @author 王强 Email : 
 * @version 创建时间：2018/8/6
 * MessageServiceImpl
 */
@Service
public class MessageServiceImpl implements MessageService{
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AmqpTemplate amqpTemplate;

    /***
     * 异步调用接口
     * @param serverName 服务名称
     * @param url url
     * @param param param
     */
    @Override
    public void async(String serverName, String url, Map<String, Object> param){
        AsyncDTO asyncDTO = new AsyncDTO();
        asyncDTO.setUrl(url);
//        asyncDTO.setParam(param);
        this.sendLog(serverName, url, param);
        this.send(serverName, asyncDTO);
    }

    @Override
    public void async(String url, Map<String, Object> param){
        try{
            throw new Exception("此方法暂未实现,请使用其他方法");
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    /***
     * 像一个服务发送一个接口的调用信息
     * @param serverName 服务名称(驼峰)
     * @param asyncDTO 消息DTO
     */
    private void send(String serverName, AsyncDTO asyncDTO){
        Method method = null;
        amqpTemplate.convertAndSend(BaseUtils.StringUtilsSon.addMarkToString(serverName,"-"), asyncDTO);
    }

    /***
     * 发送消息时的log封装
     */
    private void sendLog(String serverName, String url, Map<String, Object> param){
        logger.info(String.format("发布异步消息: serverName = %s, url = %s", serverName, url, JSONObject.toJSONString(param)));
    }
}
