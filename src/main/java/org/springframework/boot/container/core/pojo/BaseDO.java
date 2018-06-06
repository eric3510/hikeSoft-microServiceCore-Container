package org.springframework.boot.container.core.pojo;

import lombok.Data;
import org.springframework.boot.container.core.annotation.TableName;

@Data
public class BaseDO extends Paging{
    private String id;

    /***
     * 从do实例中获取表名
     * @return
     */
    public String tableName(){
        return this.getClass().getAnnotation(TableName.class).value();
    }

    /***
     * 从do class中获取表名
     * @param baseDOClass
     * @return
     */
    public static String tableName(Class<?> baseDOClass){
        return baseDOClass.getAnnotation(TableName.class).value();
    }
//    private Logger logger = LoggerFactory.getLogger(BaseDO.class);

//    private Long id;

//    public boolean equals(Object obj){
//        if(obj == this){
//            return true;
//        }
//
//        if(obj == null){
//            return false;
//        }
//        // 类型相同
//        if(obj.getClass() == this.getClass()){
//            // 当前类反射方法组
//            Method[] thisMethodGroup = this.getClass().getMethods();
//            try{
//                // 遍历反射方法组并提取当前类属性的getter方法
//                for(Method method : thisMethodGroup){
//                    // 过滤与当前类属性无关的get方法
//                    if(method.getName().startsWith("get") && !method.getName().equals("getClass")){
//                        // 将当前类属性的getter方法与比较类属性的getter方法值作比较
//                        Method currentMethod = obj.getClass().getMethod(method.getName());
//                        // 执行方法以获取返回值比较(关键点：注意参数不相同)
//                        Object objReturnValue = currentMethod.invoke(obj);
//                        Object thisReturnValue = method.invoke(this);
//                        // 空值报异
//                        if(objReturnValue == null && thisReturnValue == null){
//                            return true;
//                        }else if(objReturnValue != null && thisReturnValue != null){
//                            // 返回值不相等则返回逻辑假
//                            return objReturnValue.equals(thisReturnValue);
//                        }else{
//                            return false;
//                        }
//
//                    }
//                }
//            }catch(SecurityException ex){
//                logger.error("异常信息：参数错误，安全管理器检测到安全侵犯！\r\n" + ex.getMessage());
//            }catch(NoSuchMethodException ex){
//                logger.error("异常信息：参数错误，无法找到某一特定的方法！\r\n" + ex.getMessage());
//            }catch(IllegalArgumentException ex){
//                logger.error("异常信息：参数错误，向方法传递了一个不合法或不正确的参数！\r\n" + ex.getMessage());
//            }catch(IllegalAccessException ex){
//                logger.error("异常信息：参数错误，对象定义无法访问，无法反射性地创建一个实例！\r\n" + ex.getMessage());
//            }catch(InvocationTargetException ex){
//                logger.error("异常信息：参数错误，由调用方法或构造方法所抛出异常的经过检查的异常！\r\n" + ex.getMessage());
//            }
//        }
//
//        return false;
//
//    }


}
