package org.springframework.boot.container.core.constant;

/***
 * @author 王强 Email : eric3510@foxmail.com
 * @version 创建时间：2018/3/12
 * Constant
 */
public class Constant{
    /***
     * 业务操作结果
     */
    public enum Resule{
        FAILURE(-1, "操作失败"),
        SUCCESS(0, "操作成功");

        private Integer code;
        private String msg;

        Resule(Integer code, String msg){
            this.code = code;
            this.msg = msg;
        }

        public String msg(){
            return msg;
        }

        public Integer code(){
            return code;
        }
    }
}
