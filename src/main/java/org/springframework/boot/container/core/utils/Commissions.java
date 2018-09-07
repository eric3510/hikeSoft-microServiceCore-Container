package org.springframework.boot.container.core.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/***
 * @author 王强 Email : 
 * @version 创建时间：2018/8/8
 * 委托组 T 返回值类型
 */
public class Commissions{
    private List<Commission> commissionList = new ArrayList<>();

    class Commission<T>{
        /***
         * 方法所属的对象实例
         */
        private Object object;

        /***
         * 方法名称
         */
        private String methodName;

        /***
         * 方法参数
         */
        private Object[] args;

        public Commission(Object object, String methodName, Object[] args){
            this.object = object;
            this.methodName = methodName;
            this.args = args;
        }

        public void setObject(Object object){
            this.object = object;
        }

        public void setArgs(Object[] args){
            this.args = args;
        }

        public void setMethodName(String methodName){
            this.methodName = methodName;
        }

        public Object getObject(){
            return object;
        }

        public String getMethodName(){
            return methodName;
        }

        public Object[] getArgs(){
            return args;
        }

        /***
         * 交付
         * @return T
         */
        public T deliver(){
            Class c = this.object.getClass();
            List<Class> list = new ArrayList<>();
            for(Object o : this.args){
                list.add(o.getClass());
            }
            try{
                Method method = c.getMethod(this.methodName, list.toArray(new Class[list.size()]));
                return (T) method.invoke(this.object, this.args);
            }catch(NoSuchMethodException e){
                e.printStackTrace();
            }catch(IllegalAccessException e){
                e.printStackTrace();
            }catch(InvocationTargetException e){
                e.printStackTrace();
            }
            return null;
        }
    }

    /***
     * 添加委托
     * @param object 委托父级对象实例
     * @param methodName 方法名称
     * @param args 参数
     */
    public void addEvent(Object object, String methodName, Object... args){
        this.commissionList.add(new Commission(object, methodName, args));
    }

    /***
     * 交付
     * @return T
     */
    public List<Object> deliver(){
        List<Object> returnList = new ArrayList<>();
        for(Commission commission : commissionList){
            returnList.add(commission.deliver());
        }
        return returnList;
    }

    /***
     * 查看当前时间委托的数量
     * @return
     */
    public Integer size(){
        return this.commissionList.size();
    }
}
