package org.springframework.boot.container.core.service;


/**
 * @author 王强
 * @version 创建时间：2017/10/17 20:00
 * DaoMongoService
 **/
public interface DaoMongoService{
//    /***
//     * 获取spring原生mongo操作对象
//     * @return MongoOperations
//     */
//    MongoOperations getMongoOperations();
//
//    /***
//     * 分页
//     * @param resultClass 返回类的class
//     * @param query 查询条件
//     * @param currentPage 当前页
//     * @param pageSize 每页大小
//     * @param <T> T
//     * @return PagingVO
//     */
//    <T> PagingVO<T> listPaging(Class<T> resultClass, Query query, int currentPage, int pageSize);
//
//    /***
//     * 根据query查询一个文档队列
//     * @param resultClass 返回实体类类型
//     * @param query 查询条件
//     * @param <T> T
//     * @return list
//     */
//    <T> List<T> find(Class<T> resultClass, Query query);
//
//    /***
//     * 根据query查询一条文档
//     * @param resultClass 返回实体类类型
//     * @param query 查询条件
//     * @param <T> T
//     * @return list
//     */
//    <T> T findOne(Class<T> resultClass, Query query);
//
//    /***
//     * 根据ID查询
//     * @param resultClass 返回实体类类型
//     * @param id 主键
//     * @param <T> T
//     * @return T
//     */
//    <T> T findById(Class<T> resultClass, String id);
//
//    /***
//     * 插入一条文档
//     * @param book 实体(需添加tableName来确定文档名称)
//     */
//    void insert(Object book);
//
//    /***
//     * 批量插入文档
//     * @param modelList 文档队列
//     */
//    void saveBatch(List<?> modelList);
//
//    /***
//     * 保存一条文档
//     * @param book 文档
//     */
//    void save(Object book);
//
//    /***
//     * 更新符合条件的第一条文档
//     * @param query 条件
//     * @param update 将要修改的键值对
//     * @param resultClass 返回类型
//     * @return WriteResult
//     */
//    WriteResult updateFirst(Query query, Update update, Class<?> resultClass);
//
//    /***
//     * 根据ID更新文档
//     * @param id 主键
//     * @param update 将要修改的键值对
//     * @param resultClass 返回类型
//     * @return WriteResult
//     */
//    WriteResult updateById(String id, Update update, Class<?> resultClass);
//
//    /***
//     * 批量更新符合条件文档
//     * @param query 条件
//     * @param update 将要修改的键值对
//     * @param resultClass 返回类型
//     * @return WriteResult
//     */
//    WriteResult updateMulti(Query query, Update update, Class resultClass);
//
//    /***
//     * 移除model的文档
//     * @param book 将要移除的文档实例
//     * @return WriteResult
//     */
//    WriteResult remove(Object book);
//
//    /***
//     * 移除符合条件的文档
//     * @param query 条件
//     * @param resultClass 带有tableName注解的Class
//     * @return WriteResult
//     */
//    WriteResult remove(Query query, Class<?> resultClass);
//
//    /***
//     * 根据主键移除文档
//     * @param resultClass 带有tableName注解的类
//     * @param id 主键
//     * @return WriteResult
//     */
//    WriteResult removeById(Class<?> resultClass, String id);
//
//    /***
//     * 根据实体类获取实体类的tableName注解内容(相当于获取表名)
//     * @param resultClass 返回类型
//     * @return String
//     */
//    String getCollectionName(Class<?> resultClass);
//
//    /***
//     * 执行原生mongo shell脚本
//     * @param commandString shell字符串
//     * @return 结果
//     * @throws MongoException
//     */
//    String exec(String commandString) throws MongoException;
//
//    /***
//     * 根据原生查询shell脚本生成Query查询对象
//     * @param queryString MongoDB原生sheel脚本字符串
//     * @return Query
//     */
//    Query createQuery(String queryString);
//
//    /***
//     * 创建一个没有条件的Query条件对象
//     * @return Query
//     */
//    Query createQuery();
//
//    /***
//     * 检查是否存在具有给定名称的集合
//     * @param colName 教研的集合名称
//     * @return boolean
//     * @throws MongoException
//     */
//    boolean collectionExists(String colName) throws MongoException;
}
