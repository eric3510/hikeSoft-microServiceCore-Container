package org.springframework.boot.container.core.configs;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.io.InputStream;
import java.util.Properties;

/***
 * @author 王强 Email : 
 * @version 创建时间：2018/4/18
 * Db1Config
 */
@Configuration
@MapperScan(basePackages = "org.springframework.boot.container.core.mysql.mapper.db1", sqlSessionTemplateRef = "db1SqlSessionTemplate")
public class Db1Config{
    private static final String DB1_DATA_SOURCE_T = "db1DataSource";

    private static final String DB1_SQL_SESSION_FACTORY_T = "db1SqlSessionFactory";

    private static final String DB1_SQL_TRANSACTION_MANAGER_T = "db1TransactionManager";

    private static final String DB1_SQL_SESSION_TEMPLATE_T = "db1SqlSessionTemplate";

    private static final String LOCATION_PATTERN = "classpath:mybatis/mapper/db1/Db1Mapper.xml";

    private static final String CONFIGURATION_PROPERTIES_PREFIX = "spring.datasource.db1";

    @Bean(name = DB1_DATA_SOURCE_T)
    @ConfigurationProperties(prefix = CONFIGURATION_PROPERTIES_PREFIX)
    @Primary
    public DataSource testDataSource(){
        return DataSourceBuilder.create().build();
    }

    @Bean(name = DB1_SQL_SESSION_FACTORY_T)
    @Primary
    public SqlSessionFactory testSqlSessionFactory(@Qualifier(DB1_DATA_SOURCE_T) DataSource dataSource) throws Exception{
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(LOCATION_PATTERN));
        return bean.getObject();
    }

    @Bean(name = DB1_SQL_TRANSACTION_MANAGER_T)
    @Primary
    public DataSourceTransactionManager testTransactionManager(@Qualifier(DB1_DATA_SOURCE_T) DataSource dataSource){
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = DB1_SQL_SESSION_TEMPLATE_T)
    @Primary
    public SqlSessionTemplate testSqlSessionTemplate(@Qualifier(DB1_SQL_SESSION_FACTORY_T) SqlSessionFactory sqlSessionFactory) throws Exception{
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}