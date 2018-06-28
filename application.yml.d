spring:
#  datasource:
#    db1:
#      jdbcUrl:  @spring.datasource.db1.jdbcUrl@
#      username: @spring.datasource.db1.username@
#      password: @spring.datasource.db1.password@
#      driver-class-name: @spring.datasource.db1.driver-class-name@
#      platform: @spring.datasource.db1.platform@
#    db2:
#      jdbcUrl: @spring.datasource.db2.jdbcUrl@
#      username: @spring.datasource.db2.username@
#      password: @spring.datasource.db2.password@
#      driver-class-name: @spring.datasource.db2.driver-class-name@
#      platform: @spring.datasource.db2.platform@

  mvc:
    static-path-pattern: /api/**
  resources:
    static-locations: file:@basedir@/polestatic/api/

server:
  port: @server.port@
  context-path: /
  tomcat:
    uri-encoding: utf-8
    accesslog:
      enabled: true
      directory : @accesslog.directory@

management:
  endpoints:
    web:
      exposure:
        include: "*"
  endpoint:
    shutdown:
      enabled: true

start-up-config:
  mysql: false
  redis: false

#redis-config:
#  host: @redis-config.host@
#  port: @redis-config.port@
#  auth: @redis-config.auth@
#  maxActive: @redis-config.maxActive@
#  maxIdle: @redis-config.maxIdle@
#  maxWait: @redis-config.maxWait@
#  timeOut: @redis-config.timeOut@
#  testOnBorrow: @redis-config.testOnBorrow@