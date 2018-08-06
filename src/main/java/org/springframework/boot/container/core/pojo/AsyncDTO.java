package org.springframework.boot.container.core.pojo;

import lombok.Data;
import org.springframework.boot.container.core.constant.url.mathod.MethodData;
import org.springframework.boot.container.core.message.queue.receiver.Demo2;

import java.util.Map;

/***
 * @author 王强 Email : 
 * @version 创建时间：2018/8/6
 * AsyncDTO
 */
@Data
public class AsyncDTO extends MethodData{
    private String url;

    private BaseDTO param;

    public void demo2333(){
        System.out.println(this.param.getClass().getName());
    }

    public static void main(String[] args){
        AsyncDTO dto = new AsyncDTO();
        Demo2 demo2 = new Demo2();
        dto.setParam(demo2);
        dto.demo2333();
    }
}
