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
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/***
 * @author 王强 Email : 
 * @version 创建时间：2018/4/18
 * db2Config
 */
@Configuration
@MapperScan(basePackages = "org.springframework.boot.container.core.mysql.mapper.db2", sqlSessionTemplateRef = "db2SqlSessionTemplate")
public class Db2Config{
    private static final String DB2_DATA_SOURCE_T = "db2DataSource";

    private static final String DB2_SQL_SESSION_FACTORY_T = "db2SqlSessionFactory";

    private static final String DB2_SQL_TRANSACTION_MANAGER_T = "db2TransactionManager";

    private static final String DB2_SQL_SESSION_TEMPLATE_T = "db2SqlSessionTemplate";

    private static final String LOCATION_PATTERN = "classpath:mybatis/mapper/db2/Db2Mapper.xml";

    private static final String CONFIGURATION_PROPERTIES_PREFIX = "spring.datasource.db2";

    @Bean(name = DB2_DATA_SOURCE_T)
    @ConfigurationProperties(prefix = CONFIGURATION_PROPERTIES_PREFIX)
    public DataSource testDataSource(){
        return DataSourceBuilder.create().build();
    }

    @Bean(name = DB2_SQL_SESSION_FACTORY_T)
    public SqlSessionFactory testSqlSessionFactory(@Qualifier(DB2_DATA_SOURCE_T) DataSource dataSource) throws Exception{
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(LOCATION_PATTERN));
        return bean.getObject();
    }

    @Bean(name = DB2_SQL_TRANSACTION_MANAGER_T)
    public DataSourceTransactionManager testTransactionManager(@Qualifier(DB2_DATA_SOURCE_T) DataSource dataSource){
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = DB2_SQL_SESSION_TEMPLATE_T)
    public SqlSessionTemplate testSqlSessionTemplate(@Qualifier(DB2_SQL_SESSION_FACTORY_T) SqlSessionFactory sqlSessionFactory) throws Exception{
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}