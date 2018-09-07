package org.springframework.boot.container.core.annotation.micro;

import java.lang.annotation.*;

/***
 * @author 王强 Email : 
 * @version 创建时间：2018/8/28
 * ClockServer
 */
@Inherited
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface ClockServer{
}
