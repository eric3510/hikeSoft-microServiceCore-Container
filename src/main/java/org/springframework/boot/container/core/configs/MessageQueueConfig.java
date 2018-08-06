package org.springframework.boot.container.core.configs;

import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.container.core.SpringContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/***
 * @author 王强 Email : 
 * @version 创建时间：2018/8/3
 * 根据服务名称注入一个队列,并绑定到交换机上
 */
@Data
@Configuration
public class MessageQueueConfig{
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private static ConnectionFactory connectionFactory;
    /***
     * 地址
     */
    @Value("${message-queue.hostName}")
    private String hostName;

    /***
     * 端口
     */
    @Value("${message-queue.port}")
    private int port;

    /***
     * 用户名
     */
    @Value("${message-queue.userName}")
    private String userName;

    /***
     * 密码
     */
    @Value("${message-queue.password}")
    private String password;

    @Value("${message-queue.virtualHost}")
    private String virtualHost;

    @Value("${message-queue.publisherConfirms}")
    private boolean publisherConfirms;

    private static final String BASE_EXCHANGE = "baseExchange";

    private static final String MESSAGES = "messages";
    /***
     * 生成队列
     * @return
     */
    @Bean(name = MESSAGES)
    public Queue queue(){
        return new Queue(ServerConfig.getConfig().getSpringApplicationName());
    }

    /***
     * 创建交换机
     * @return
     */
    @Bean
    public TopicExchange exchange(){
        return new TopicExchange(BASE_EXCHANGE);
    }

    /***
     * 将队列和交换机绑定
     * @param queueMessages
     * @param exchange
     * @return
     */
    @Bean
    Binding bindingExchangeMessages(@Qualifier(MESSAGES) Queue queueMessages, TopicExchange exchange){
        return BindingBuilder.bind(queueMessages).to(exchange).with(BASE_EXCHANGE);
    }

    @Bean
    public ConnectionFactory connectionFactory(){
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(hostName, port);
        logger.info("userName " + userName);
        connectionFactory.setUsername(userName);
        connectionFactory.setPassword(password);
        connectionFactory.setVirtualHost(virtualHost);
        connectionFactory.setPublisherConfirms(publisherConfirms); // 必须要设置
        return connectionFactory;
    }

    public static synchronized ConnectionFactory getConnectionFactory(){
        if(connectionFactory == null){
            connectionFactory = SpringContext.getBean(ConnectionFactory.class);
        }
        return connectionFactory;
    }
}
