package org.springframework.boot.container.core.applicaiton;

import lombok.Data;

/***
 * @author 王强 Email : 
 * @version 创建时间：2018/7/12
 * RunThread
 */
@Data
public class RunThread extends Thread{
    public static boolean statusBool = false;
    private String url;
    private String result;
    public RunThread(String url){
        this.url = url;
    }
}
