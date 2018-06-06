package org.springframework.boot.container.core.service;

import org.springframework.boot.container.core.pojo.PagingVO;
import org.springframework.boot.container.core.pojo.TreeVO;

import java.util.List;
import java.util.Map;

/**
 * @author 王强
 * @version 创建时间：2017/08/18 10:29
 * DaoMysqlService
 **/
public interface DaoMysqlService{
    /***
     * 原生查询sql
     * @param type 返回类型
     * @param sql sql
     * @param paramMap 参数map集合
     * @param <T> 泛型 T
     * @return return return
     */
    <T> List<T> list(Class<T> type, String sql, Map<String, Object> paramMap);

    /***
     * 根据DTO中的字段查询
     * @param <T> 返回类型
     * @param type 返回类型
     * @param param 参数
     * @return list集合
    s*/
    <T> List<T> list(Class<T> type, Object param);

    /***
     * 根据DTO中的字段查询
     * @param <T> 返回类型
     * @param type 返回类型
     * @param param 参数
     * @return list集合
     */
    <T> T get(Class<T> type, Object param);

    /***
     * 根据DTO中的字段查询
     * @param <T> 返回类型
     * @param param 参数
     * @return list集合
     */
    @Deprecated
    <T> T get(Object param);

    /***
     * 求一个sql的查询总条数
     * @param sql sql
     * @param param 参数
     * @return 总条数
     */
    int count(String sql, Map<String, Object> param);

    /***
     * 根据DO或者DTO对象查询数量
     * @param param 参数业务独享
     * @return 数量
     */
    int count(Object param);

    /***
     * 根据多个id字段查询多个对象
     * @param type 返回对象类型
     * @param ids id list集合
     * @param tableName 表名
     * @param <T> 泛型 返回类型
     * @return return
     */
    <T> List<T> listByIds(Class<T> type, List<String> ids, String tableName);


    /***
     * 根据多个字段名为fieldName的字段值查询多个对象
     * @param type 返回对象类型
     * @param key 字段名称
     * @param values 字段值集合
     * @param tableName 表名
     * @param <T> 泛型
     * @return return
     */
    <T> List<T> listByKeys(Class<T> type, String key, List<?> values, String tableName);

    /***
     * 分页查询
     * @param type 返回类型（必须继承VO）
     * @param toPage 所跳页
     * @param pageSize 每页条数
     * @param sql sql
     * @param param 查询参数
     * @return 带有分页信息的POJO
     */
    <T> PagingVO<T> listPaging(Class<T> type, Integer toPage, Integer pageSize, String sql, Map<String, Object> param);

    /***
     * 单表分页查询
     * @param type 返回累心
     * @param param 参数
     * @param <T>
     * @return 分页对象
     */
    <T> PagingVO<T> listPaging(Class<T> type, Object param);

    /***
     * 根据多个字段名为fieldName的字段值查询多个对象
     * @param type 返回对象类型
     * @param key 字段名称
     * @param value 字段值
     * @param tableName 表名
     * @param <T> 泛型 泛型
     * @return return reutn
     */
    <T> T getByKey(Class<T> type, String key, Object value, String tableName);

    /***
     * 根据多个id字段查询多个对象
     * @param type 返回对象类型
     * @param id id值
     * @param tableName 表名
     * @param <T> 泛型 返回类型
     * @return return
     */
    <T> T getById(Class<T> type, String id, String tableName);

    <T> T getById(Class<T> type, String id);

    /***
     * 批量添加
     * @param list list元素名称必须与数据库中表名相同格式必须为驼峰例如：数据库中：demo_table, 实体类名：demoTable
     * @return return
     */
    int saveBatch(List<? extends Object> list);

    /***
     * 添加一条记录
     * @param object 元素名称或者tableName注解的值必须与数据库中表名相同格式必须为驼峰例如：数据库中：demo_table, 实体类名：demoTable
     * @return return
     */
    int save(Object object);


    /***
     * 添加一个实体类
     * @param tableName 表名
     * @param object dao实体类对象
     * @return return
     */
    int save(Object object, String tableName);

    /***
     * 更新数据原生sql
     * @param sql sql
     * @param map 参数map集合
     * @return return
     */
    int update(String sql, Map<String, Object> map);

    /***
     * 批量修改id队列
     * @param idList id队列
     * @param updateSet 要修改的字段
     * @param tableName 表名
     * @return return
     */
    int updateByIds(List<String> idList, Map<String, Object> updateSet, String tableName);


    /***
     *  根据主键id修改一条记录
     * @param id 主键
     * @param updateSet 要更新字段的内容
     * @param tableName 表名
     * @return 影响行数
     */
    int updateById(String id, Map<String, Object> updateSet, String tableName);

    /***
     *
     */
    int updateById(Object updateSet);


    int updateById(Object updateSet, String tableName);

    /***
     * 根据keyName字段对应的值修改一批记录
     * @param key key 值
     * @param keyName 字段名称
     * @param updateSet 要更新的字段
     * @param tableName 表名
     * @return 受影响行数
     */
    int updateByKey(Object key, String keyName, Map<String, Object> updateSet, String tableName);

    /***
     * 根据一个字段值的队列批量修改
     * @param keyList key队列
     * @param keyName filed name
     * @param updateSet 要修改的字段
     * @param tableName 表名
     * @return 影响行数
     */
    int updateByKeys(List<?> keyList, String keyName, Map<String, Object> updateSet, String tableName);

    /***
     * update条件为单表的查询
     * @param updateSql 跟新数据sql
     * @param setUpdate 数据更新的参数
     * @return 受影响行数
     */
    int update(String updateSql, Object setUpdate);

    /***
     * 删除记录原生sql
     * @param sql sql
     * @param paramMap 条件
     * @return 收影响行数
     */
    int remove(String sql, Map<String, Object> paramMap);


    /***
     * 根据id队列批量删除
     * @param idList id队列
     * @param tableName 表名
     * @return 影响行数
     */
    int removeByIds(List<String> idList, String tableName);

    /***
     * 根据主键id删除一条记录
     * @param id 主键
     * @param tableName 表名
     * @return 影响行数
     */
    int removeById(String id, String tableName);

    /***
     * 根据一个字段值的队列批量修改
     * @param keyList key队列
     * @param keyName filed name
     * @param tableName 表名
     * @return return
     */
    int removeByKeys(List<?> keyList, String keyName, String tableName);

    /***
     * 根据一个key值删除一批纪录
     * @param key key值
     * @param keyName 字段名称
     * @param tableName 表名
     * @return 影响行数
     */
    int removeByKey(Object key, String keyName, String tableName);

    /***
     * 树结构查询
     * @param type 返回类型
     * @param sql sql查询语句
     * @param param 参数
     * @param <T>
     * @return
     */
    <T> TreeVO<T> listTree(Class<T> type, String sql, Map<String, Object> param);
}
