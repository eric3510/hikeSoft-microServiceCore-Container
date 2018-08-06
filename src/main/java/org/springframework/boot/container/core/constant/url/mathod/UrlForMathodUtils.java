package org.springframework.boot.container.core.constant.url.mathod;

/***
 * @author 王强 Email : 
 * @version 创建时间：2018/8/6
 * Url
 */
public class UrlForMathodUtils{


    public static MethodData getMethodDataToUrl(String url){
        if(null == UrlForMathod.getMethodDataMap()){
            try{
                throw new Exception("UrlForMathodUtils未初始化,不能使用！");
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        return UrlForMathod.getMethodDataMap().get(url);
    }
}
