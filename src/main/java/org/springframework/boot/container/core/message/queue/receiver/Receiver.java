package org.springframework.boot.container.core.message.queue.receiver;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.boot.container.core.SpringContext;
import org.springframework.boot.container.core.configs.MessageQueueConfig;
import org.springframework.boot.container.core.configs.ServerConfig;
import org.springframework.boot.container.core.constant.url.mathod.MethodData;
import org.springframework.boot.container.core.constant.url.mathod.UrlForMathod;
import org.springframework.boot.container.core.pojo.AsyncDTO;
import org.springframework.boot.container.core.pojo.ServerResponse;
import org.springframework.boot.container.core.utils.BaseUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;

/***
 * @author 王强 Email : 
 * @version 创建时间：2018/8/3
 * Receiver
 */
public class Receiver{
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    /***
     * 监听一个队列
     * @param queueName 队列名称为服务名称
     */
    private void rabbitListener(String queueName) {
        //获取消息连接工厂
        ConnectionFactory connectionFactory = MessageQueueConfig.getConnectionFactory();
        //设置监听
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
        Object listener = new Object(){
            public void handleMessage(byte[] bytes){
                AsyncDTO asyncDTO = null;
                try{
                    asyncDTO  = (AsyncDTO) BaseUtils.Serialization.getObjectFromBytes(bytes);
                    logger.info(String.format("以接收消息: %s" , JSONObject.toJSONString(asyncDTO)));
                    ServerResponse serverResponse = urlTransferMethod(asyncDTO);
                    logger.info(JSONObject.toJSONString(serverResponse));
                }catch(Exception e){
                    e.printStackTrace();
                }

            }
        };

        MessageListenerAdapter adapter = new MessageListenerAdapter(listener);
        container.setMessageListener(adapter);
        //监听名称为当前服务的队列
        container.setQueueNames(queueName);
        container.start();
    }

    /***
     * 通过异步调用DTO中url的属性获取对应controller method中方法的名称，
     * 然后从spring上下文中获取该controller实例对象,通过methodData中method方法名获取方法，
     * 最后通过反射调用
     * @param asyncDTO asyncDTO
     * @return ServerResponse
     */
    private static ServerResponse urlTransferMethod(AsyncDTO asyncDTO){
        String url = asyncDTO.getUrl();
        MethodData methodData = UrlForMathod.getMethodDataToUrl(url);
        Class c = null;
        try{
            c = Class.forName(methodData.getClassName());
        }catch(ClassNotFoundException e){
            e.printStackTrace();
        }
        Object controllerObj = SpringContext.getBean(c);
        Method m = null;
        ServerResponse serverResponse = null;
        try{
//            Map<String, Object> paramMap = asyncDTO.getParam();
            m = c.getMethod(methodData.getMethod());
//            serverResponse = (ServerResponse) m.invoke(controllerObj, );
        }catch(Exception e){
            e.printStackTrace();
        }
        return serverResponse;
    }

    public void mapToList(Map<String, Object> paramMap, List<Object> list){

        Iterator iterator = paramMap.entrySet().iterator();

    }

    /***
     * 以服务名称监听
     */
    public void rabbitListener(){
        String serverName = ServerConfig.getConfig().getSpringApplicationName();
        this.rabbitListener(serverName);
    }

//    public void demo(){
//
//        CachingConnectionFactory cf = new CachingConnectionFactory("192.168.1.101", 5672);
//        cf.setUsername("admin");
//        cf.setPassword("admin");
//        cf.setVirtualHost("/");
//        cf.setPublisherConfirms(true); // 必须要设置
//
//        RabbitAdmin admin = new RabbitAdmin(cf);
//        //创建队列
//        Queue queue = new Queue("myQueue");
//        admin.declareQueue(queue);
//
//        //创建topic类型的交换机
//        TopicExchange exchange = new TopicExchange("myExchange");
//        admin.declareExchange(exchange);
//        //交换机和队列绑定，路由规则为匹配"foo."开头的路由键
//        admin.declareBinding(BindingBuilder.bind(queue).to(exchange).with("foo.*"));
//
//        //设置监听
//        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(cf);
//        Object listener = new Object(){
//            public void handleMessage(String foo){
//                System.out.println(" [x] Received '" + foo + "'");
//            }
//        };
//        MessageListenerAdapter adapter = new MessageListenerAdapter(listener);
//        container.setMessageListener(adapter);
//        container.setQueueNames("myQueue");
//        container.start();
//
//        //发送消息
//        RabbitTemplate template = new RabbitTemplate(cf);
//        for(int i = 0; i < 5; i++){
//            try{
//                Thread.sleep(1000);
//                template.convertAndSend("myExchange", "foo.bar", "Hello, world!");
//            }catch(InterruptedException e){
//                e.printStackTrace();
//            }
//        }
//        try{
//            Thread.sleep(1000);
//        }catch(InterruptedException e){
//            e.printStackTrace();
//        }
//        container.stop();
//    }
}



//@Component
//@RabbitListener(queues = "hello")
//public class Receiver{
//    @RabbitHandler
//    public void porcess(byte[] bytes) throws Exception{
//        DemoDO demoDO = (DemoDO) MessageFactoryUtils.getObjectFromBytes(bytes);
//        System.out.println("接收者 : " + JSONObject.toJSONString(demoDO));
//    }
//}