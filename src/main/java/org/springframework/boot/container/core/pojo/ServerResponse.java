package org.springframework.boot.container.core.pojo;

import lombok.Data;

/***
 * @author 王强 Email :
 * @version 创建时间：2018/4/3
 * ServerResponse
 */
@Data
public class ServerResponse{
    public final static int SUCCESS = 0;

    /***
     * 操作失败
     */
    public final static int ERROR = -1;

    /***
     * 业务异常
     */
    public final static int ERROR_BBUSINESS = 1;

    /***
     * 服务错误
     */
    public final static int ERROR_SERVER = 2;

    /***
     * 网络错误
     */
    public final static int ERROR_NETWORK = 3;

    public final static String SUCCESS_MSG = "操作成功";
    public final static String ERROR_MSG = "操作失败";

    private int code = 0;

    private String msg;

    private Object data;

    public int getCode(){
        return code;
    }

    public void setCode(int code){
        this.code = code;
    }

    public String getMsg(){
        return msg;
    }

    public void setMsg(String msg){
        this.msg = msg;
    }

    public Object getData(){
        return data;
    }

    public void setData(Object data){
        this.data = data;
    }

    public void setSuccess(Object data){
        this.code = SUCCESS;
        this.msg = SUCCESS_MSG;
        this.data = data;
    }

    public void setSuccess(){
        this.code = SUCCESS;
        this.msg = SUCCESS_MSG;
    }

    public void setError(){
        this.code = ERROR;
        this.msg = ERROR_MSG;
    }
}
