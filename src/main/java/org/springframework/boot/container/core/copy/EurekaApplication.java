package org.springframework.boot.container.core;

import java.util.List;

/***
 * @author 王强 Email : 
 * @version 创建时间：2018/7/10
 * EurekaApplication
 */
public abstract class EurekaApplication extends BaseApplication{
    public void run(Object source, String... args){
        List<String> list = this.getParamList("eurekaTest", args);
        super.run(source, list.toArray(new String[list.size()]));
    }
}










