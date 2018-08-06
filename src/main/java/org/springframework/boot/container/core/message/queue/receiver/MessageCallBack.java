package org.springframework.boot.container.core.message.queue.receiver;

import java.io.Serializable;

/***
 * @author 王强 Email : 
 * @version 创建时间：2018/8/3
 * MessageCallBack 回调接口
 */
public class MessageCallBack implements Serializable{
    //void callBack(Map<String,Object> callBackParam, ServerResponse response);
    void callBack(){}
}
