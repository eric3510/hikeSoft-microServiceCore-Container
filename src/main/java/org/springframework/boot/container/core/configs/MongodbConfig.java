package org.springframework.boot.container.core.configs;

import org.springframework.context.annotation.Configuration;

/**
 * @author 王强 eric3510@foxmail.com
 * @version 创建时间：2017/10/17 15:00
 * mongodb连接池
 **/
@Configuration
//@EnableMongoRepositories(basePackages = {"com.icu.core.configs"})
public class MongodbConfig {//extends AbstractMongoConfiguration
    /***
     * 用户名称
     */
    //@Value("${mongo.username}")
    private String userName;

    /***
     * 数据库名称
     */
    //@Value("${mongo.database.name}")
    private String databaseName;

    /***
     * 密码
     */
    //@Value("${mongo.password}")
    private String password;

    /***
     * 端口号
     */
    //@Value("${mongo.port}")
    private int port;

    /***
     * 网络地址
     */
    //@Value("${mongo.host}")
    private String host;

    /***
     * 每个主机的连接数
     */
    //@Value("${mongo.connections.per.host}")
    private Integer connectionsPerHost;

    /***
     * 线程队列数
     */
//    //@Value("${mongo.thread.count}")
    private Integer threadCount;

    /***
     * 最大等待连接的线程阻塞时间(单位:毫秒)
     */
//    //@Value("${mongo.max.wait.time}")
    private Integer maxWaitTime;

    /***
     * 连接超时的时间.0是默认和无限(单位:毫秒)
     */
//    //@Value("${mongo.time.out}")
    private Integer timeOut;

    protected String getDatabaseName(){
        return this.databaseName;
    }

//    private MongoClientOptions getConfOptions(){
//        return new MongoClientOptions.Builder().socketKeepAlive(true) // 是否保持长链接
//                .connectTimeout(this.timeOut) // 链接超时时间
//                .connectionsPerHost(connectionsPerHost) // 每个地址最大请求数
//                .maxWaitTime(maxWaitTime) // 长链接的最大等待时间
//                .threadsAllowedToBlockForConnectionMultiplier(50) // 一个socket最大的等待请求数
//                .build();
//    }
//
//    public Mongo mongo() throws Exception{
//        MongoCredential credential = MongoCredential.createScramSha1Credential(
//                this.userName,
//                this.databaseName,
//                this.password.toCharArray()
//        );
//        MongoClient mongoClient = new MongoClient(
//                new ServerAddress(this.host, this.port),
//                Arrays.asList(credential),
//                this.getConfOptions()
//        );
//        return mongoClient;
//    }
//
//    public static void main(String[] args){
//        MongoClientOptions mongoClientOptions = new MongoClientOptions.Builder().socketKeepAlive(true) // 是否保持长链接
//                .connectTimeout(1000) // 链接超时时间
//                .connectionsPerHost(20) // 每个地址最大请求数
//                .maxWaitTime(1000) // 长链接的最大等待时间
//                .threadsAllowedToBlockForConnectionMultiplier(50) // 一个socket最大的等待请求数
//                .build();
//        MongoCredential credential = MongoCredential.createScramSha1Credential(
//                "root",
//                "admin",
//                "^Jb7^6cH$gZzh7DN".toCharArray()
//        );
//        MongoClient mongoClient = new MongoClient(
//                new ServerAddress(
//                        "dds-2ze28fcb570723e41891-pub.mongodb.rds.aliyuncs.com",
//                        3717
//                ),
//                Arrays.asList(credential),
//                mongoClientOptions
//        );
//        String name = mongoClient.getDatabase("icu2").getName();
//        System.out.println(name);
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("name", "demo");
//        Demo2 demo2 = new Demo2();
//        demo2.setName("name");
//        String demo3 = mongoClient.getMongoClientOptions().getDescription();
//        System.out.println(demo3);
//    }
}
