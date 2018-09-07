package org.springframework.boot.container.core.annotation.micro;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.container.core.annotation.micro.ComponentScanConstant;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@EnableScheduling
@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(basePackages = {
        ComponentScanConstant.CONTAINER_CORE,
        ComponentScanConstant.TXJ_SERVER,
        ComponentScanConstant.TASK_RELEASE_SERVER,
        ComponentScanConstant.TASK_SCHEDULING_CONFIG_SERVER,
        ComponentScanConstant.CLOCK_SERVER,
        ComponentScanConstant.INIT,
})
public @interface MicroServer{
}
