package org.springframework.boot.container.core.annotation.micro;

import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.ComponentScan;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static org.springframework.boot.container.core.annotation.micro.ComponentScanConstant.CONTAINER_CORE;
import static org.springframework.boot.container.core.annotation.micro.ComponentScanConstant.MICROSERVICE_SERVER;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@EnableZuulProxy
@SpringCloudApplication
@ComponentScan(basePackages = {CONTAINER_CORE, MICROSERVICE_SERVER})
public @interface ZuulServer{
}
