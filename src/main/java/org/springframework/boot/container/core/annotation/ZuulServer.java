package org.springframework.boot.container.core.annotation;

import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.ComponentScan;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@EnableZuulProxy
@SpringCloudApplication
@ComponentScan(basePackages = {"org.springframework.boot.container.core","microservice.server"})
public @interface ZuulServer{
}
