package org.springframework.boot.container.core;

import org.springframework.boot.container.core.annotation.micro.ClockServer;
import org.springframework.boot.container.core.annotation.micro.TaskStorageServer;

import java.util.ArrayList;
import java.util.List;

/***
 * @author 王强 Email : 
 * @version 创建时间：2018/8/1
 * ServerApplication
 */
public abstract class ServerApplication extends BaseApplication{
    @Override
    public void run(Object source, String... args){
        Class c = (Class)source;
        List<String> argList = new ArrayList<>();
        for(String arg : args){
            argList.add(arg);
        }
        if(c.getAnnotation(ClockServer.class) != null){
            argList.add("--message-queue.clock=true");
        }
        if(c.getAnnotation(TaskStorageServer.class) != null){
            argList.add("--message-queue.storageServer=true");
        }else{
            argList.add("--message-queue.storageServer=false");
        }
        super.run(source, argList.toArray(new String[argList.size()]));
    }
}
