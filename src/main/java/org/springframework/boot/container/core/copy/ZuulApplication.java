package org.springframework.boot.container.core;

import java.util.List;

/***
 * @author 王强 Email : 
 * @version 创建时间：2018/7/17
 * ZuulApplication
 */
public abstract class ZuulApplication extends BaseApplication{
    @Override
    public void run(Object source, String... args){
        List<String> list = getParamList("zuulTest", args);
        super.runZuul(source, list.toArray(new String[list.size()]));
    }
}
