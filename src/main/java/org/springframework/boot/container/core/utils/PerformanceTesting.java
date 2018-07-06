package org.springframework.boot.container.core.utils;

import java.lang.reflect.Method;

/***
 * @author 王强 Email : 
 * @version 创建时间：2018/7/5
 * PerformanceTesting
 */
public class PerformanceTesting{
    public void runTestSegment(Object object, String methodName){
//        Thread thread = new Thread(() -> {
            Class c = object.getClass();
            Method method = null;
            try{
                method = c.getMethod(methodName);
                long start = System.currentTimeMillis();
                method.invoke(object);
                long end = System.currentTimeMillis();
                System.out.println("用时 " + methodName + " :" + (end - start));
            }catch(Exception e){
                e.printStackTrace();
            }
//        });
//        thread.start();
//        System.out.println("yes");
    }
}
