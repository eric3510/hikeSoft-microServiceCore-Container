package org.springframework.boot.container.core.annotation;

import java.lang.annotation.*;

/***
 * @author 王强 Email : eric3510@foxmail.com
 * @version 创建时间：2017/9/8
 * 当字段有此注解时，sql中的字段名称会优先设置为此注解的value参数的值
 */
@Inherited
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface FieldName{
    String value() default "";
}
