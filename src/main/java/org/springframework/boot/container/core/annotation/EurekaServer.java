package org.springframework.boot.container.core.annotation;

import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@EnableScheduling
//@EnableEurekaServer
@SpringBootApplication
@ComponentScan(basePackages = {"org.springframework.boot.container.core","microservice.server"})
public @interface EurekaServer{
}
