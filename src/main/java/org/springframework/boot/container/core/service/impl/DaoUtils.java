package org.springframework.boot.container.core.service.impl;

import org.springframework.boot.container.core.annotation.TableName;
import org.springframework.boot.container.core.utils.BaseUtils;

public class DaoUtils{
    private static final String UNDERLINE_T = "_";

    /***
     * 根据一个实体类获取到表名
     * @param object object
     * @return String
     */
    static String getTableName(Object object){
        return DaoUtils.getTableNameClass(object.getClass());
    }

    /***
     * 根据Class获取类名
     * @param c
     * @return 表名
     */
    static String getTableNameClass(Class c){
        if(c.isAnnotationPresent(TableName.class))
            return ((TableName) c.getAnnotation(TableName.class)).value();
        return BaseUtils.StringUtilsSon.addMarkToString(
                c.getSimpleName().replace("DO", ""),
                DaoUtils.UNDERLINE_T
        );
    }

    public static void main(String[] args){
        String a = "123";
        String c = "123";
        String b = new String("123");
        System.out.println(a == c);
        System.out.println(a.equals(b));
    }
}
