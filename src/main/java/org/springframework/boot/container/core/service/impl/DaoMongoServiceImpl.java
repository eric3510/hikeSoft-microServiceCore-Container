package org.springframework.boot.container.core.service.impl;

import com.mongodb.CommandResult;
import com.mongodb.MongoException;
import com.mongodb.WriteResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.container.core.pojo.PagingVO;
import org.springframework.boot.container.core.service.DaoMongoService;
import org.springframework.boot.container.core.utils.BaseUtils;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 王强
 * @version 创建时间：2017/10/17 20:00
 * DaoMongoServiceImpl
 **/
@Service
public class DaoMongoServiceImpl implements DaoMongoService{
    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private MongoOperations mongoOperations;

    /***
     * 获取spring原生mongo操作对象
     * @return mongoOperations
     */
    public MongoOperations getMongoOperations(){
        return mongoOperations;
    }

    /***
     * 获取spring mongo模板
     * @return MongoTemplate
     */
    public MongoTemplate getMongoTemplate(){
        return mongoTemplate;
    }

   

    public <T> PagingVO<T> listPaging(Class<T> resultClass, Query query, int currentPage, int pageSize){
        int total = (int) this.getMongoOperations().count(query, getCollectionName(resultClass));
        if(total < 1)
            return new PagingVO<T>();
        if(currentPage < 1){
            currentPage = 1;
        }
        if(pageSize < 1){
            pageSize = total;
        }
        query = query.limit(pageSize).skip((currentPage - 1) * pageSize);
        List<T> result = this.getMongoOperations().find(query, resultClass, getCollectionName(resultClass));
        PagingVO<T> pagingVO = new PagingVO<>();
        pagingVO.setCurrentPage(currentPage);
        pagingVO.setTotal(total);
        pagingVO.setPageTotal(PagingVO.getPageTotal(total, pageSize));
        pagingVO.setList(result);
        return pagingVO;
    }

    /***
     * 根据query查询一个文档队列
     * @param resultClass 返回实体类类型
     * @param query 查询条件
     * @param <T> T
     * @return list
     */
    public <T> List<T> find(Class<T> resultClass, Query query){
        return this.getMongoOperations().find(query, resultClass, getCollectionName(resultClass));
    }

    /***
     * 根据query查询一条文档
     * @param resultClass 返回实体类类型
     * @param query 查询条件
     * @param <T> T
     * @return list
     */
    public <T> T findOne(Class<T> resultClass, Query query){
        return this.getMongoOperations().findOne(query, resultClass, getCollectionName(resultClass));
    }

    /***
     * 根据ID查询
     * @param resultClass 返回实体类类型
     * @param id 主键
     * @param <T> T
     * @return T
     */
    public <T> T findById(Class<T> resultClass, String id){
        return this.getMongoOperations().findById(id, resultClass, getCollectionName(resultClass));
    }

    /***
     * 插入一条文档
     * @param book 实体(需添加tableName来确定文档名称)
     */
    public void insert(Object book){
        this.getMongoOperations().insert(book, getCollectionName(book.getClass()));
    }

    /***
     * 批量插入文档
     * @param modelList 文档队列
     */
    public void saveBatch(List<?> modelList){
        this.getMongoOperations().insert(modelList, getCollectionName(modelList.get(0).getClass()));
    }

    /***
     * 保存一条文档
     * @param book 文档
     */
    public void save(Object book){
        this.getMongoOperations().save(book, getCollectionName(book.getClass()));
    }

    /***
     * 更新符合条件的第一条文档
     * @param query 条件
     * @param update 将要修改的键值对
     * @param resultClass 返回类型
     * @return WriteResult
     */
    public WriteResult updateFirst(Query query, Update update, Class<?> resultClass){
        return this.getMongoOperations().updateFirst(query, update, getCollectionName(resultClass));
    }

    /***
     * 根据ID更新文档
     * @param id 主键
     * @param update 将要修改的键值对
     * @param resultClass 返回类型
     * @return WriteResult
     */
    public WriteResult updateById(String id, Update update, Class<?> resultClass){
        Query query = new Query();
        query.addCriteria(new Criteria("_id").is(id));
        return this.updateFirst(query, update, resultClass);
    }

    /***
     * 批量更新符合条件文档
     * @param query 条件
     * @param update 将要修改的键值对
     * @param resultClass 返回类型
     * @return WriteResult
     */
    public WriteResult updateMulti(Query query, Update update, Class resultClass){
        return this.getMongoOperations().updateMulti(query, update, getCollectionName(resultClass));
    }

    /***
     * 移除model的文档
     * @param book 将要移除的文档实例
     * @return WriteResult
     */
    public WriteResult remove(Object book){
        return this.getMongoOperations().remove(book, getCollectionName(book.getClass()));
    }

    /***
     * 移除符合条件的文档
     * @param query 条件
     * @param resultClass 带有tableName注解的Class
     * @return WriteResult
     */
    public WriteResult remove(Query query, Class<?> resultClass){
        return this.getMongoOperations().remove(query, getCollectionName(resultClass));
    }

    /***
     * 根据主键移除文档
     * @param resultClass 带有tableName注解的类
     * @param id 主键
     * @return WriteResult
     */
    public WriteResult removeById(Class<?> resultClass, String id){
        return this.remove(this.findById(resultClass, id));
    }

    /***
     * 根据实体类获取实体类的tableName注解内容(相当于获取表名)
     * @param resultClass 返回类型
     * @return String
     */
    public String getCollectionName(Class<?> resultClass){
        return DaoUtils.getTableNameClass(resultClass);
    }

    /***
     * 检查是否存在具有给定名称的集合
     * @param colName 教研的集合名称
     * @return boolean
     * @throws MongoException
     */
    public boolean collectionExists(String colName) throws MongoException{
        return this.getMongoOperations().collectionExists(colName);
    }

    /***
     * 执行原生mongo shell脚本
     * @param commandString shell字符串
     * @return 结果
     * @throws MongoException
     */
    public String exec(String commandString) throws MongoException{
        CommandResult result = this.getMongoOperations().executeCommand(commandString);
        if(result != null && result.ok()){
            return result != null ? result.toString() : null;
        }else{
            throw new MongoException("mongo exec command error:" + result != null ? result.getErrorMessage() : "null");
        }
    }

    /***
     * 根据原生查询shell脚本生成Query查询对象
     * @param queryString MongoDB原生sheel脚本字符串
     * @return Query
     */
    public Query createQuery(String queryString){
        return new BasicQuery(BaseUtils.isNotBlank(queryString) ? queryString : "{}");
    }

    /***
     * 创建一个没有条件的Query条件对象
     * @return Query
     */
    public Query createQuery(){
        return createQuery(null);
    }
}
