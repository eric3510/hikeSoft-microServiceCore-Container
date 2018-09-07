package org.springframework.boot.container.core.annotation.micro;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static org.springframework.boot.container.core.annotation.micro.ComponentScanConstant.CONTAINER_CORE;
import static org.springframework.boot.container.core.annotation.micro.ComponentScanConstant.MICROSERVICE_SERVER;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@EnableScheduling
@EnableEurekaServer
@SpringBootApplication
@ComponentScan(basePackages = {CONTAINER_CORE, MICROSERVICE_SERVER})
public @interface EurekaServer{
}
