package org.springframework.boot.container.core.init;

import com.alibaba.fastjson.JSONObject;

import java.io.*;

/***
 * @author 王强 Email : 
 * @version 创建时间：2018/5/18
 * DocApi
 */
public class DocApi{
    static public void createApi(String path){
        try{
            String execStr = String.format("apidoc -i %s -o %s/polestatic/api/", path, path);
            Process p = Runtime.getRuntime().exec(execStr);
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
