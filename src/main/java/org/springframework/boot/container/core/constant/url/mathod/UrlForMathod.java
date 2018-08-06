package org.springframework.boot.container.core.constant.url.mathod;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.condition.RequestMethodsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.HashMap;
import java.util.Map;

/***
 * @author 王强 Email : 
 * @version 创建时间：2018/8/6
 * UrlForMathod
 */
@Component
public class UrlForMathod{
    @Autowired
    private RequestMappingHandlerMapping requestMappingHandlerMapping;

    private static Map<String, MethodData> methodDataMap;

    public synchronized void init(){
        if(methodDataMap == null) methodDataMap = getMethodDataMapInit();
    }

    public static Map<String, MethodData> getMethodDataMap(){
        return methodDataMap;
    }

    /***
     * 获取所有接口url与controller方法的对应关系
     * @return Map
     */
    private Map<String, MethodData> getMethodDataMapInit(){
        Map<String, MethodData> methodDataMap = new HashMap<>();
        Map<RequestMappingInfo, HandlerMethod> map = requestMappingHandlerMapping.getHandlerMethods();
        for (Map.Entry<RequestMappingInfo, HandlerMethod> m : map.entrySet()) {
            MethodData methodData = new MethodData();
            RequestMappingInfo info = m.getKey();
            HandlerMethod method = m.getValue();
            PatternsRequestCondition p = info.getPatternsCondition();
            for (String url : p.getPatterns()) {
                methodData.setUrl(url);
            }
            methodData.setClassName(method.getMethod().getDeclaringClass().getName());
            methodData.setMethod(method.getMethod().getName());
            RequestMethodsRequestCondition methodsCondition = info.getMethodsCondition();
            String type = methodsCondition.toString();
            if (type != null && type.startsWith("[") && type.endsWith("]")) {
                type = type.substring(1, type.length() - 1);
                methodData.setType(type);
            }
            methodDataMap.put(methodData.getUrl(), methodData);
        }
        return methodDataMap;
    }

    public static MethodData getMethodDataToUrl(String url){
        if(null == methodDataMap){
            try{
                throw new Exception("UrlForMathodUtils未初始化,不能使用！");
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        return methodDataMap.get(url);
    }
}
