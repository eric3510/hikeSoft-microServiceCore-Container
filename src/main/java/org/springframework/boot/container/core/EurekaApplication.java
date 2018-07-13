package org.springframework.boot.container.core;

import org.springframework.boot.container.core.utils.BaseUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/***
 * @author 王强 Email : 
 * @version 创建时间：2018/7/10
 * EurekaApplication
 */
public abstract class EurekaApplication extends BaseApplication{
    public void run(Object source, String... args){
        List<String> list = new ArrayList<>();
        Map<String, String> paramMap = new HashMap<>();
        for(String arg : args){
            this.paramToMap(arg, paramMap);
        }
        String profiles = paramMap.get(SPRING_PROFILES_ACTIVE);
        if(BaseUtils.isBlank(profiles)){
            paramMap.put(SPRING_PROFILES_ACTIVE, "eureka");
        }
        this.setParam(paramMap, list);
        super.run(source, list.toArray(new String[list.size()]));
    }
}










