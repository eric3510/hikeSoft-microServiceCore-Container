package org.springframework.boot.container.core.configs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.container.core.utils.BaseUtils;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/***
 * @author 王强 Email : eric3510@foxmail.com
 * @version 创建时间：2017/12/5
 * TimerConfig 定时任务轮配置
 */
@Component
public class TimerConfig{
    private Logger logger = LoggerFactory.getLogger(getClass());


    /***
     * 请求编码格式
     */
    public static String encode;

    /***
     * 校验token(哈希值)
     */
    public static String token;

    /***
     * url地址
     */
    public static String urlAddress;

    /***
     * 测试环境ip地址
     */
    public static String testIp;

    /***
     * 任务轮发布任务消费者所监听的队列
     */
    public static String hashedWheelProducerTask = "hashed-wheel-producer-task";

    public static final String POST = "POST";

    public static final String GET = "GET";

    public TimerConfig(){
    }

    //@Value("${timer.hashed.wheel.producer.task}")
    public void setHashedWheelProducerTask(String hashedWheelProducerTask){
        this.hashedWheelProducerTask = hashedWheelProducerTask;
    }

    //@Value("${timer.encode}")
    public void setEncode(String encode){
        TimerConfig.encode = encode;
    }

    //@Value("${timer.token}")
    public void setToken(String token){
        TimerConfig.token = token;
    }

    //@Value("${timer.url.address}")
    public void setUrlAddress(String urlAddress){
        TimerConfig.urlAddress = urlAddress;
    }

    //@Value("${timer.test.ip}")
    public void setTestIp(String testIp){
        TimerConfig.testIp = testIp;
    }

    @PostConstruct
    public void init(){
        this.isIp();
    }

    private void isIp(){
        String testIp = TimerConfig.testIp;
        //判断是否为测试或者本地环境
        if(BaseUtils.isBlank(testIp)){
            return;
        }
        logger.info("testIp = " + testIp);
        //判断是否在本地运行
        String localIp = this.getLocalIp();
        logger.info("localIp = " + localIp);
        if(localIp != null){//判定为本地的话则生成隔离环境地址
            TimerConfig.urlAddress = "http://" + localIp + ":8080/";
        }
//        if(!testIp.equals(localIp)){//判定为本地的话则生成隔离环境地址
//            TimerConfig.urlAddress = "http://" + localIp + ":8080/";
//        }
    }


    /***
     * 获取本地IP
     * @return
     */
    private String getLocalIp(){
        Enumeration<NetworkInterface> n = null;
        try{
            n = NetworkInterface.getNetworkInterfaces();
        }catch(SocketException e){
            e.printStackTrace();
        }
        for(; n.hasMoreElements(); ){
            NetworkInterface e = n.nextElement();
            Enumeration<InetAddress> a = e.getInetAddresses();
            if(!e.getName().equals("en1")){
                continue;
            }
            for(; a.hasMoreElements(); ){
                String ip = a.nextElement().getHostAddress();
                String[] ips = ip.split("\\.");
                if(ips.length == 4){
                    return ip;
                }
            }
        }
        return null;
    }

    public static void main(String[] args){
        TimerConfig config = new TimerConfig();
        System.out.println(config.getLocalIp());
//        final String url = "http://preadmin.hushijie.com.cn/demo/producer";
////        final String url = ""
//        final Map<String, String> param = new HashMap<>();
//        param.put("seconds", "20");
//        param.put("count", "1");
//
//        Thread[] threads = new Thread[5];
//        for (Thread thread : threads) {
//            thread = new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    for (int i = 0; i < 10; i++) {
//                        HttpClientUtil.doPost(url, param, "UTF-8");
//                    }
//                    System.out.println("ok");
//                }
//            });
//            thread.start();
//        }
    }

}
