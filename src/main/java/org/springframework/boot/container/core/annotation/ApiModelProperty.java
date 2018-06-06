package org.springframework.boot.container.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/***
 * api校验
 */
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiModelProperty {
    String value() default "";

    String name() default "";

    String allowableValues() default "";

    String access() default "";

    String notes() default "";

    String dataType() default "";

    boolean required() default false;

    int position() default 0;

    boolean hidden() default false;

    String example() default "";

    boolean readOnly() default false;

    String reference() default "";
}
