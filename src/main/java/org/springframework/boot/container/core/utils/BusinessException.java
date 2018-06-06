package org.springframework.boot.container.core.utils;

import org.springframework.boot.container.core.pojo.ServerResponse;

/**
 * @author 王强 eric3510@foxmail.com
 * @version 创建时间：2017/08/01 13:50
 * BusinessException
 **/
public final class BusinessException extends RuntimeException {

    private static final long serialVersionUID = 1721891525581654383L;

    private int code = ServerResponse.ERROR_BBUSINESS;

    private Object data;

    public BusinessException(){
        super();
    }

    public BusinessException(Exception ex){
        super(ex.getMessage());
        if(ex instanceof BusinessException){
            this.code=((BusinessException) ex).getCode();
            this.data=((BusinessException) ex).getData();
        }
    }
    public BusinessException(Exception ex,String msg){
        super(msg);
        if(ex instanceof BusinessException){
            this.code=((BusinessException) ex).getCode();
            this.data=((BusinessException) ex).getData();
        }
    }

    public BusinessException(String msg){
        super(msg);
    }

    public BusinessException(String msg,int code,Object data){
        super(msg);
        this.code=code;
        this.data=data;
    }

    public String getMessage() {
        return super.getMessage();
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}