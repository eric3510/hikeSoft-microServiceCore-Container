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

    public static void main(String[] args){
//        String s = "123";
//        String s2 = "123";
//        String s0 = new String("123");
        String s1 = new String("123");
//        s0 = s0.intern();
        System.out.println("123" == s1.intern());
    }
}
