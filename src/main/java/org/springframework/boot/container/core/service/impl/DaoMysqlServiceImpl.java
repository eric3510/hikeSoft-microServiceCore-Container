package org.springframework.boot.container.core.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.container.core.annotation.*;
import org.springframework.boot.container.core.mysql.SaveResult;
import org.springframework.boot.container.core.mysql.SqlTemplate;
import org.springframework.boot.container.core.mysql.mapper.BaseMapper;
import org.springframework.boot.container.core.pojo.*;
import org.springframework.boot.container.core.service.DaoMysqlService;
import org.springframework.boot.container.core.utils.BaseUtils;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.sql.Timestamp;
import java.util.*;

/**
 * @author 王强
 * @version 创建时间：2017/08/18 10:30
 * DaoMysqlServiceImpl
 **/
@Service
public class DaoMysqlServiceImpl implements DaoMysqlService{
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final String ID_T = "id";

    private static final String UNDERLINE_T = "_";

    private static final String SQL_T = "sql";

    private static final String INDEX_T = "index";

    private static final String ARRAY_T = "#ARRAY";

    private static final String COMMA = ", ";

    private static final int NEGATIVE_ONE = -1;

    private static final String OPERATING_COUNT = " COUNT(*) ";

    private static final String OPERATING_ALL = " * ";

    private static final String COLON = ":";

    private static final String MARK_SPLIT = "\\|%a%b%c%\\|";

    private static final String TREE_LEVEL_ANNO_NULL_MSG = "没有检测到任何字段存在TreeLevel注解,无法创建树结构";

    private static final String TREE_LEVEL_ANNO_NULL_MSG_2 = "被TreeLevel注解的字段必须为Integer类型";

    private static final String TREE_PARENT_ID_ANNO_NULL_MSG = "没有检测到任何字段存在TreeParentId注解,无法创建树结构";

    private BaseMapper baseMapper;

    public DaoMysqlServiceImpl setBaseMapperGetService(BaseMapper baseMapper){
        this.baseMapper = baseMapper;
        return this;
    }

    /***
     * 根据多个字段名为fieldName的字段值查询多个对象
     * @param type 返回对象类型
     * @param key 字段名称
     * @param value 字段值
     * @param tableName 表名
     * @param <T> 泛型 泛型
     * @return return reutn
     */
    public <T> T getByKey(Class<T> type, String key, Object value, String tableName){
        List<Object> values = new ArrayList<>();
        values.add(value);
        List<T> list = this.listByKeys(type, key, values, tableName);
        if(list == null){
            return null;
        }
        if(list.size() == 0){
            return null;
        }
        return list.get(0);
    }

    /***
     * 根据多个字段名为fieldName的字段值查询多个对象
     * @param type 返回对象类型
     * @param key 字段名称
     * @param values 字段值集合
     * @param tableName 表名
     * @param <T> 泛型
     * @return return
     */
    public <T> List<T> listByKeys(Class<T> type, String key, List<?> values, String tableName){
        if(values == null){
            return new ArrayList<>();
        }
        if(values.size() == 0){
            return new ArrayList<>();
        }
        SqlTemplate sqlTemplate = new SqlTemplate();
        sqlTemplate.setObjectList(values);
        sqlTemplate.setTableName(tableName);
        sqlTemplate.setObject(key);
        List<Map<String, Object>> list = baseMapper.listByKeys(sqlTemplate);

        return this.getResultList(list, type);
    }

    /***
     * 根据多个id字段查询多个对象
     * @param type 返回对象类型
     * @param ids id list集合
     * @param tableName 表名
     * @param <T> 泛型 返回类型
     * @return return
     */
    public <T> List<T> listByIds(Class<T> type, List<String> ids, String tableName){
        return this.listByKeys(type, DaoMysqlServiceImpl.ID_T, ids, tableName);
    }

    /***
     * 根据多个id字段查询多个对象
     * @param type 返回对象类型
     * @param id id值
     * @param tableName 表名
     * @param <T> 泛型 返回类型
     * @return return
     */
    public <T> T getById(Class<T> type, String id, String tableName){
        return this.getByKey(type, DaoMysqlServiceImpl.ID_T, id, tableName);
    }

    @Override
    public <T> T getById(Class<T> type, String id){
        String tableName = type.getAnnotation(TableName.class).value();
        return this.getById(type, id, tableName);
    }

    /***
     * 原生查询sql
     * @param type 返回类型
     * @param sql sql
     * @param paramMap 参数map集合
     * @param <T> 泛型 T
     * @return return return
     */
    public <T> List<T> list(Class<T> type, String sql, Map<String, Object> paramMap){
        SqlTemplate sqlTemplate = new SqlTemplate();
        sqlTemplate.setSql(DaoMysqlServiceImpl.getInSql(sql, paramMap));
        sqlTemplate.setParamMap(paramMap);
        List<Map<String, Object>> list = baseMapper.select(sqlTemplate);
        if(list == null){
            return null;
        }
        return buildResultList(type, list);
    }

    /***
     * 根据DTO中的字段查询
     * @param <T> 返回类型
     * @param type 返回类型
     * @param param 参数
     * @return list集合
     */
    public <T> List<T> list(Class<T> type, Object param){
        this.checkBaseDODTO(param);
        TableName tableNameAnno = param.getClass().getAnnotation(TableName.class);
        String sql = this.getSelectSql(tableNameAnno, param, DaoMysqlServiceImpl.OPERATING_ALL);
        return this.list(type, sql, DaoMysqlServiceImpl.toHashMap(param));
    }

    /***
     * 根据DTO中的字段查询
     * @param <T> 返回类型
     * @param type 返回类型
     * @param param 参数
     * @return list集合
     */
    public <T> T get(Class<T> type, Object param){
        List<T> list = this.list(type, param);
        if(list == null){
            return null;
        }
        if(list.size() == 0){
            return null;
        }
        return list.get(0);
    }

    public <T> T get(Object param){
        return (T) this.get(param.getClass(), param);
    }


    /***
     * 获取单表查询sql
     * @param param 参数
     * @return sql
     */
    private String getSelectSql(TableName tableNameAnno, Object param, String operating){
        String sql;
        String tableName = tableNameAnno.value();
        String[] sqls = {"SELECT ", " FROM ", " WHERE 1 = 1 "};
        sql = sqls[1] + tableName + sqls[2];
        switch(operating){
            case DaoMysqlServiceImpl.OPERATING_ALL:
                sql = sqls[0] + DaoMysqlServiceImpl.OPERATING_ALL + sql;
                break;
            case DaoMysqlServiceImpl.OPERATING_COUNT:
                sql = sqls[0] + DaoMysqlServiceImpl.OPERATING_COUNT + sql;
                break;
            default:
                this.throwException("operating参数值错误");
                return null;
        }
        return this.getSelectWhereSql(param, sql);
    }

    /***
     * 获取where查询条件
     * @param param
     * @param sql
     * @return
     */
    private String getSelectWhereSql(Object param, String sql){
        List<String> fieldNameList = DaoMysqlServiceImpl.getNotNullFieldNames(param);
        int size = fieldNameList.size();
        for(int i = 0; i < size; i++){
            String fieldName = fieldNameList.get(i);
            String tableFieldName = DaoMysqlServiceImpl.getFileName(fieldName);
            sql += " AND " + tableFieldName + "= #{" + fieldName + "} ";
        }
        return sql;
    }

    /***
     * 查询数量
     * @param sql sql
     * @param param 参数
     * @return
     */
    public int count(String sql, Map<String, Object> param){
        SqlTemplate sqlTemplate = new SqlTemplate();
        sqlTemplate.setSql(DaoMysqlServiceImpl.getInSql(sql, param));
        sqlTemplate.setParamMap(param);
        return baseMapper.count(sqlTemplate);
    }

    /***
     * 根据DO或者DTO对象查询数量
     * @param param 参数业务独享
     * @return 数量
     */
    public int count(Object param){
        this.checkBaseDODTO(param);
        TableName tableNameAnno = param.getClass().getAnnotation(TableName.class);
        String sql = this.getSelectSql(tableNameAnno, param, DaoMysqlServiceImpl.OPERATING_COUNT);
        Map<String, Object> paramMap = new HashMap<>();
        BaseUtils.copyMap(paramMap, param);
        return this.count(sql, paramMap);
    }

    /***
     * 分页查询
     * @param type 返回类型
     * @param toPage 所跳页
     * @param pageSize 每页条数
     * @param sql sql
     * @param param 查询参数
     * @return 带有分页信息的POJO
     */
    public <T> PagingVO<T> listPaging(Class<T> type, Integer toPage, Integer pageSize, String sql, Map<String, Object> param){
        String selectT = "SELECT";
        String fromT = "FROM";
        if(toPage == null){
            toPage = 1;
        }
        if(pageSize == null){
            pageSize = 10000;
        }
        int total = this.count(selectT + " COUNT(*) " + sql.substring(sql.indexOf(fromT), sql.length()), param);
        sql = sql + " LIMIT " + ((toPage - 1) * pageSize) + "," + pageSize;
        List<T> list = this.list(type, sql, param);
        PagingVO<T> pagingVO = new PagingVO<>();
        pagingVO.setList(list);
        pagingVO.setCurrentPage(toPage);
        pagingVO.setPageTotal((total % pageSize) > 0 ? total / pageSize + 1 : total / pageSize);
        pagingVO.setTotal(total);
        return pagingVO;
    }

    /***
     * 单表分页查询
     * @param type 返回累心
     * @param param 参数
     * @param <T>
     * @return 分页对象
     */
    public <T> PagingVO<T> listPaging(Class<T> type, Object param){
        this.checkBaseDODTO(param);
        TableName tableNameAnno = param.getClass().getAnnotation(TableName.class);
        String sql = this.getSelectSql(tableNameAnno, param, DaoMysqlServiceImpl.OPERATING_ALL);
        Paging paging = (Paging) param;
        sql = sql + " " + paging.getOrder() + " ";
        return this.listPaging(type, paging.getToPage(), paging.getPageSize(), sql, DaoMysqlServiceImpl.toHashMap(param));
    }

    /***
     * 普通树结构查询
     * @param sql sql查询语句
     * @param param 参数
     * @param <T>
     * @return
     */
    public <T> TreeVO<T> listTree(Class<T> type, String sql, Map<String, Object> param){
        AnnotationDTO annotationDTO = new AnnotationDTO();
        String errorMsg = this.checkTreeType(type, annotationDTO);
        if(BaseUtils.isNotBlank(errorMsg)){
            logger.error(errorMsg);
            return null;
        }
        List<T> list = this.list(type, sql, param);
        TreeVO<T> treeVO = new TreeVO<>();
        List<Tree<T>> treeList = new ArrayList<>();
        for(T node : list){
            Tree<T> tree = new Tree<>();
            tree.setNode(node);
            treeList.add(tree);
        }
        treeVO.setTreeList(treeList);
        return treeVO;
    }


    /***
     * 校验是否有树结构的相关注解
     * @param type
     * @param annotationDTO
     * @return
     */
    private String checkTreeType(Class type, AnnotationDTO annotationDTO){
        Field[] fields = type.getDeclaredFields();
        boolean treeLevelBool = true;
        boolean treeParentIdBool = true;
        for(Field field : fields){
            String fieldName = field.getName();
            if(treeLevelBool){
                TreeLevel treeLevel = field.getAnnotation(TreeLevel.class);
                if(treeLevel != null){
                    if(field.getType() != Integer.class){
                        return TREE_LEVEL_ANNO_NULL_MSG_2;
                    }
                    annotationDTO.setTreeLevelFieldName(fieldName);
                    treeLevelBool = false;
                }
            }

            if(treeParentIdBool){
                if(field.getAnnotation(TreeParentId.class) != null){
                    annotationDTO.setTreeParentIdFieldName(fieldName);
                    treeParentIdBool = false;
                }
            }
        }
        if(treeLevelBool){
            return TREE_LEVEL_ANNO_NULL_MSG;
        }

        if(treeParentIdBool){
            return TREE_PARENT_ID_ANNO_NULL_MSG;
        }
        return null;
    }

    /***
     * 将Object对象转换为Map集合
     * @param object
     * @return Map
     */
    private static Map<String, Object> toHashMap(Object object){
        Map<String, Object> map = new HashMap<>();
        BaseUtils.copyMap(map, object);
        return map;
    }

    /***
     * 获取不为空的字段的名称
     * @param object
     * @return String[]
     */
    private static List<String> getNotNullFieldNames(Object object){
        List<String> fieldNameList = new ArrayList<>();
        String[] fieldNames = DaoMysqlServiceImpl.getObjectFieldNames(object.getClass());
        for(String fieldName : fieldNames){
            if(BaseUtils.ObjectUtils.invokeMethod(object, "get" + BaseUtils.StringUtilsSon.firstLetterUpperCase(fieldName.contains(DaoMysqlServiceImpl.COLON) ? fieldName = fieldName.split(DaoMysqlServiceImpl.COLON)[0] : fieldName)) == null){
                continue;
            }
            fieldNameList.add(fieldName);
        }
        return fieldNameList;
    }

    private <T> List<T> buildResultList(Class<T> type, List<Map<String, Object>> list){
        if(list.size() != 0){
            if(DaoMysqlServiceImpl.checkType(type)){
                String keyName = null;
                Map<String, Object> keyNameMap = list.get(0);
                for(Map.Entry<String, Object> entry : keyNameMap.entrySet()){
                    if(keyName == null){
                        keyName = entry.getKey();
                    }else{
                        break;
                    }
                }
                List<Object> returnList = new ArrayList<>();
                for(Map<String, Object> map : list){
                    returnList.add(map.get(keyName));
                }
                return BaseUtils.JsonUtils.jsonArrayToList(returnList, type);
            }
        }
        return this.getResultList(list, type);
    }

    /***
     * 批量添加
     * @param list 将要添加到数据库中的集合
     * @param tableName 表名
     * @return return 受影响行数
     */
    private SaveResult saveBatch(List<?> list, String tableName){
        SqlTemplate saveDO = new SqlTemplate();
        if(list == null){
            return new SaveResult();
        }
        if(list.size() == 0){
            return new SaveResult();
        }
        if(BaseUtils.isBlank(tableName)){
            tableName = DaoMysqlServiceImpl.getTableName(list.get(0));
        }
        saveDO.setTableName(tableName);
        String[] saveFieldNames = null;
        List<Map<String, Object>[]> saveValues = new ArrayList<>();
        String id = "";
        for(Object obj : list){
            this.checkBaseDOException(obj);
            saveFieldNames = DaoMysqlServiceImpl.getObjectFieldNames(obj.getClass());
            Map<String, Object> map = new HashMap<>();
            BaseUtils.copyMap(map, obj);
            id = map.get("id") + "";
            Map<String, Object>[] objects = new HashMap[saveFieldNames.length];
//            objects[0] = this.getSaveMap("VARCHAR", this.getId(obj));
            int i = 0;
            for(String saveField : saveFieldNames){
                Object value = map.get(saveField);
                objects[i] = this.getSaveMap(DaoMysqlServiceImpl.getJdbcType(value), getTableFieldName(saveFieldNames, map, i, saveField, value));
                i++;
            }
            saveValues.add(objects);
        }
        for(int i = 0; i < (saveFieldNames != null ? saveFieldNames.length : 0); i++){
            saveFieldNames[i] = DaoMysqlServiceImpl.getFileName(saveFieldNames[i]);
        }
        saveDO.setSaveFieldNames(saveFieldNames);
        saveDO.setSaveValues(saveValues);
        SaveResult saveResult = new SaveResult();
        saveResult.setInfluencesRow(baseMapper.saveBatch(saveDO));
        saveResult.setId(id);
        return saveResult;
    }

    private Map<String, Object> getSaveMap(String type, Object value){
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("type", type);
        paramMap.put("value", value);
        return paramMap;
    }

    private String getId(Object idObj){
        String id = null;
        try{
            Method getIdMethod = idObj.getClass().getMethod("getId");
            id = (String)getIdMethod.invoke(idObj);
            return id;
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /***
     * 批量添加
     * @param list list元素名称必须与数据库中表名相同格式必须为驼峰例如：数据库中：demo_table, 实体类名：demoTable
     * @return return
     */
    public int saveBatch(List<?> list){
        return this.saveBatch(list, null).getInfluencesRow();
    }

    /***
     * 添加一个实体类
     * @param object dao实体类对象
     * @return return
     */
    public int save(Object object){
        return this.save(object, null);
    }

    /***
     * 添加一个实体类
     * @param object dao实体类对象
     * @return return
     */
    public int save(Object object, String tableName){
        List<Object> list = new ArrayList<>();
        list.add(object);
        SaveResult saveResult = new SaveResult();
        saveResult = this.saveBatch(list, tableName);
        int influencesRow = saveResult.getInfluencesRow();
        String setIdT = "setId";
        try{
            Class paramType = object.getClass().getMethod("getId").getReturnType();
            Method method = object.getClass().getMethod(setIdT, paramType);
            String type = paramType.getName();
            if(type.equals(Integer.class.getName())){
                method.invoke(object, Integer.parseInt(String.valueOf(saveResult.getId())));
            }else if(type.equals(Long.class.getName())){
                method.invoke(object, Long.parseLong(String.valueOf(saveResult.getId())));
            }else if(type.equals(String.class.getName())){
                method.invoke(object, String.valueOf(saveResult.getId()));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return influencesRow;
    }

    private Method getMethod(Object object, String name){
        Method[] mds = object.getClass().getMethods();
        for(Method method : mds){
            if(method.getName().equals(name)){
                return method;
            }
        }
        return null;
    }

    /***
     * 更新数据原生sql
     * @param sql sql
     * @param map 参数map集合
     * @return return
     */
    public int update(String sql, Map<String, Object> map){
        SqlTemplate sqlTemplate = new SqlTemplate();
        sqlTemplate.setSql(DaoMysqlServiceImpl.getInSql(sql, map));
        sqlTemplate.setParamMap(map);
        return baseMapper.update(sqlTemplate);
    }

    /***
     * 批量修改id队列
     * @param ids id队列
     * @param updateSet 要修改的字段
     * @param tableName 表名
     * @return return
     */
    public int updateByIds(List<String> ids, Map<String, Object> updateSet, String tableName){
        return this.updateByKeys(ids, "id", updateSet, tableName);
    }

    /***
     *  根据主键id修改一条记录
     * @param id 主键
     * @param updateSet 要更新字段的内容
     * @param tableName 表名
     * @return 影响行数
     */
    public int updateById(String id, Map<String, Object> updateSet, String tableName){
        List<String> idList = new ArrayList<>();
        idList.add(id);
        return this.updateByIds(idList, updateSet, tableName);
    }

    private void fieldListToMap(Object object, Map<String, Object> paramMap){
        Class c = object.getClass();
        Field[] fields = c.getDeclaredFields();
        for(Field field : fields){
            String fieldName = field.getName();
            Object fieldObj = paramMap.get(fieldName);
            if(fieldObj == null){
                continue;
            }
            FieldName fieldNameAnno = field.getAnnotation(FieldName.class);
            if(fieldNameAnno != null){
                String dbFieldName = fieldNameAnno.value();
                this.replaceMapKeyName(paramMap, fieldName, dbFieldName);
                paramMap.remove(fieldName);
            }
        }
    }

    public int updateById(Object updateSet, String tableName){
        Map<String, Object> resultMap = new HashMap<>();
        BaseUtils.copyMap(resultMap, updateSet);
        TableName tableNameAnno = updateSet.getClass().getAnnotation(TableName.class);
        this.removeMapField(resultMap, BaseDO.class);
        this.removeMapField(resultMap, Paging.class);
        resultMap.remove("id");
        this.removeNullField(resultMap);
        this.fieldListToMap(updateSet, resultMap);
        return this.updateById(this.getId(updateSet), resultMap, tableName == null ? tableNameAnno.value() : tableName);
    }

    public int updateById(Object updateSet){
        return this.updateById(updateSet, null);
    }

    /***
     * 移除map中的class类对应的字段名称的元素
     * @param paramMap map
     * @param c class
     */
    private void removeMapField(Map<String, Object> paramMap, Class c){
        Field[] fields = c.getDeclaredFields();
        for(Field field : fields){
            paramMap.remove(field.getName());
        }
    }

    /***
     * 移除为空的字段
     */
    private void removeNullField(Map<String, Object> paramMap){
        Map<String, Object> resultMap = new HashMap<>();
        for(Map.Entry<String, Object> entry : paramMap.entrySet()){
            if(entry.getValue() != null){
                resultMap.put(entry.getKey(), entry.getValue());
            }
        }
        paramMap.clear();
        paramMap.putAll(resultMap);
    }

    /***
     * 根据keyName字段对应的值修改一批记录
     * @param key key 值
     * @param keyName 字段名称
     * @param updateSet 要更新的字段
     * @param tableName 表名
     * @return 受影响行数
     */
    public int updateByKey(Object key, String keyName, Map<String, Object> updateSet, String tableName){
        List<Object> keyList = new ArrayList<>();
        keyList.add(key);
        return this.updateByKeys(keyList, keyName, updateSet, tableName);
    }

    /***
     * 根据一个字段值的队列批量修改
     * @param keyList key队列
     * @param keyName filed name
     * @param updateSet 要修改的字段
     * @param tableName 表名
     * @return 影响行数
     */
    public int updateByKeys(List<?> keyList, String keyName, Map<String, Object> updateSet, String tableName){
        StringBuilder sql = new StringBuilder("UPDATE " + tableName + " SET ");
        int size = updateSet.size();
        int i = 0;
        for(Map.Entry<String, Object> entry : updateSet.entrySet()){
            String key = entry.getKey();
            sql.append("`")
                    .append(key)
                    .append("`=#{")
                    .append(key)
                    .append(this.getJdbcType(updateSet.get(key)))
                    .append("}")
                    .append(i != size - 1 ? DaoMysqlServiceImpl.COMMA : "");
            i++;
        }
        updateSet.put(keyName, keyList.toArray());
        return this.update(DaoMysqlServiceImpl.getByKeysSql(sql.toString(), keyName), updateSet);
    }

    /***
     * update条件为单表的查询
     * @param updateSql set sql
     * @param setUpdate where参数
     * @return 受影响行数
     */
    public int update(String updateSql, Object setUpdate){
        updateSql += " WHERE 1 = 1 ";
        updateSql = this.getSelectWhereSql(setUpdate, updateSql);
        Map<String, Object> paramMap = new HashMap<>();
        BaseUtils.copyMap(paramMap, setUpdate);
        return this.update(updateSql, paramMap);
    }


    /***
     * 删除记录原生sql
     * @param sql sql
     * @param paramMap 条件
     * @return 收影响行数
     */
    public int remove(String sql, Map<String, Object> paramMap){
        SqlTemplate sqlTemplate = new SqlTemplate();
        sqlTemplate.setSql(DaoMysqlServiceImpl.getInSql(sql, paramMap));
        sqlTemplate.setParamMap(paramMap);
        return baseMapper.remove(sqlTemplate);
    }

    /***
     * 根据id队列批量删除
     * @param idList id队列
     * @param tableName 表名
     * @return 影响行数
     */
    public int removeByIds(List<String> idList, String tableName){
        return this.removeByKeys(idList, "id", tableName);
    }

    /***
     * 根据主键id删除一条记录
     * @param id 主键
     * @param tableName 表名
     * @return 影响行数
     */
    public int removeById(String id, String tableName){
        List<String> idList = new ArrayList<>();
        idList.add(id);
        return this.removeByIds(idList, tableName);
    }

    /***
     * 根据一个字段值的队列批量修改
     * @param keyList key队列
     * @param keyName filed name
     * @param tableName 表名
     * @return return
     */
    public int removeByKeys(List<?> keyList, String keyName, String tableName){
        String sql = "DELETE FROM " + tableName;
        Map<String, Object> map = new HashMap<>();
        map.put(keyName, keyList.toArray());
        return this.remove(DaoMysqlServiceImpl.getByKeysSql(sql, keyName), map);
    }

    /***
     * 根据一个key值删除一批纪录
     * @param key key值
     * @param keyName 字段名称
     * @param tableName 表名
     * @return 影响行数
     */
    public int removeByKey(Object key, String keyName, String tableName){
        List<Object> keyList = new ArrayList<>();
        keyList.add(key);
        return this.removeByKeys(keyList, keyName, tableName);
    }

    private static List<Map<String, Object>> getQueryCondition(BaseDO baseDO){
        List<Map<String, Object>> mapList = new ArrayList<>(15);
        String[] saveFieldNames = DaoMysqlServiceImpl.getObjectFieldNames(baseDO.getClass());
        Map<String, Object> map = new HashMap<>();
        BaseUtils.copyMap(map, baseDO);
        int i = 0;
        for(String saveField : saveFieldNames){
            Map<String, Object> paramMap = new HashMap<>();

            Object param = map.get(saveField);
            param = getTableFieldName(saveFieldNames, map, i, saveField, param);
            //有值则加入查询参数
            if(param != null){
                paramMap.put("value", param);
                String type;
                type = DaoMysqlServiceImpl.getJdbcType(param);
                paramMap.put("type", type);

                saveField = getFileName(saveField);
                paramMap.put("field", saveField);

                mapList.add(paramMap);
            }
            i++;
        }
        return mapList;

    }

    private static Object getTableFieldName(String[] saveFieldNames, Map<String, Object> map, int i, String saveField, Object param){
        if(param == null){
            //前面是字段本身的名字
            if(saveField.contains(DaoMysqlServiceImpl.COLON)){
                String[] saveFieldNameCo = saveField.split(DaoMysqlServiceImpl.COLON);
                param = map.get(saveFieldNameCo[0]);
                saveFieldNames[i] = saveFieldNameCo[1];
            }
        }
        return param;
    }

    /***
     * 获取IN操作的sql
     * @param map 参数集合
     * @param markMap 递归参数
     * @return return
     */
    private static Map<String, Object> getInSql(Map<String, Object> map, Map<String, Object> markMap){
        String leftBigBrackets = "{";
        String rightBigBrackets = "}";
        String mark = "#";
        String point = ".";
        String sql = (String) markMap.get(DaoMysqlServiceImpl.SQL_T);
        int index = (int) markMap.get(DaoMysqlServiceImpl.INDEX_T);
        int indexIn = sql.indexOf(ARRAY_T, index);
        sql = BaseUtils.StringUtilsSon.replaceRange(
                sql,
                point,
                DaoMysqlServiceImpl.UNDERLINE_T,
                leftBigBrackets, rightBigBrackets
        );
        DaoMysqlServiceImpl.replaceMapKeyName(map, point, DaoMysqlServiceImpl.UNDERLINE_T);
        if(indexIn == DaoMysqlServiceImpl.NEGATIVE_ONE){
            markMap.put(DaoMysqlServiceImpl.SQL_T, sql);
            return markMap;
        }
        String keyStr = sql.substring(
                sql.indexOf(leftBigBrackets, indexIn),
                sql.indexOf(rightBigBrackets, indexIn) + 1
        );
        String key = keyStr.substring(keyStr.indexOf(leftBigBrackets) + 1, keyStr.indexOf(rightBigBrackets)).trim();
        Object[] objects = (Object[]) map.get(key);
        key = leftBigBrackets + key;
        StringBuilder inSql = new StringBuilder();
        for(int i = 0; i < objects.length; i++){
            inSql.append(mark).append(key).append("[").append(i).append("]").append(rightBigBrackets);
            if(i == objects.length - 1){
                inSql.append("");
            }else{
                inSql.append(DaoMysqlServiceImpl.COMMA);
            }
        }
        sql = sql.replace(keyStr, inSql.toString());
        markMap.put(DaoMysqlServiceImpl.SQL_T, sql);
        markMap.put(INDEX_T, sql.indexOf(ARRAY_T, index) + ARRAY_T.length());
        DaoMysqlServiceImpl.getInSql(map, markMap);
        return markMap;
    }

    /***
     * 获取IN操作的sql
     * @param map 参数集合
     * @param sql 参数
     * @return return
     */
    private static String getInSql(String sql, Map<String, Object> map){
        Map<String, Object> markMap = new HashMap<>();
        markMap.put(DaoMysqlServiceImpl.SQL_T, sql);
        markMap.put(DaoMysqlServiceImpl.INDEX_T, 0);
        return ((String) DaoMysqlServiceImpl.getInSql(map, markMap)
                .get(DaoMysqlServiceImpl.SQL_T))
                .replaceAll("[{]", SqlTemplate.PARAM_MAP_T)
                .replace(DaoMysqlServiceImpl.ARRAY_T, "");
    }

    /***
     * 将map中的key的名称修改为根据某个标记编辑成驼峰的形式，并通过jsonUtil返回成class对象
     * @param map map
     * @param type type
     * @param <T> 泛型
     * @return return
     */
    private <T> T getResult(Map<String, Object> map, Class<T> type){
        //去除三方插件的字段
        map.remove("logger");
        return BaseUtils.JsonUtils.jsonToHumpToObject(map, type.getSimpleName().equals("Map") ? DaoMysqlServiceImpl.MARK_SPLIT : DaoMysqlServiceImpl.UNDERLINE_T, type);
    }

    /***
     * 获取驼峰lsit
     * @param list 数据源
     * @param type 类型
     * @param <T> T
     * @return T List
     */
    private <T> List<T> getResultList(List<Map<String, Object>> list, Class<T> type){
        List<T> returnList = new ArrayList<>();
        for(Map<String, Object> map : list){
            returnList.add(this.getResult(map, type));
        }
        return returnList;
    }

    /***
     * 校验对象是否为或继承BaseDO
     * @param object object
     * @return {true:是}{false:否}
     */
    private boolean checkBaseDO(Object object){
        return object instanceof BaseDO;
    }

    private void checkBaseDODTO(Object object){
        if(!(object instanceof BaseDTO) && !(object instanceof BaseDO)){
            try{
                throw new Exception("paramDTO必须继承BaseDTO或者BaseDO");
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }


    /***
     * 校验对象是否为或继承BaseDO，如果不是抛出异常
     * @param object object
     */
    private void checkBaseDOException(Object object){
        if(!this.checkBaseDO(object)){
            this.throwException("DO实体类必须继承BaseDO");
        }
    }


    /***
     * 抛出一个内容为 DO实体类必须继承BaseDO 的异常
     * @param msg 异常信息
     */
    private void throwException(String msg){
        try{
            msg = new String(msg.getBytes(), "UTF-8");
            throw new Exception(msg);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    /***
     * 拼接根据key队列查询的sql
     * @param sql sql
     * @param keyName sql
     * @return String
     */
    private static String getByKeysSql(String sql, String keyName){
        return sql + " WHERE " + keyName + " IN(" + DaoMysqlServiceImpl.ARRAY_T + "{" + keyName + "})";
    }

    /***
     * 根据一个实体类获取到表名
     * @param object object
     * @return String
     */
    private static String getTableName(Object object){
        Class c = object.getClass();
        if(c.isAnnotationPresent(TableName.class))
            return ((TableName) c.getAnnotation(TableName.class)).value();
        return BaseUtils.StringUtilsSon.addMarkToString(
                object.getClass().getSimpleName(),
                DaoMysqlServiceImpl.UNDERLINE_T
        );
    }

    /***
     * 将java驼峰命名的字段名改为数据库中下划线字段名
     * @param fieldName java中的字段名称
     * @return String
     */
    private static String getFileName(String fieldName){
        return BaseUtils.StringUtilsSon.addMarkToString(fieldName, DaoMysqlServiceImpl.UNDERLINE_T);
    }


    /***
     * 获取jdbcType
     * @param object object
     * @return String
     */
    private static String getJdbcType(Object object){
        String type = ",jdbcType=";
        if(object instanceof String){
            type += "VARCHAR";
        }else if(object instanceof Integer){
            type += "INTEGER";
        }else if(object instanceof Long){
            type += "BIGINT";
        }else if(object instanceof Date){
            type += "TIMESTAMP";
        }else if(object instanceof Timestamp){
            type += "TIMESTAMP";
        }else{
            type = "";
        }
        return type;
    }

    /***
     * 替换map中key的值，如果有变化则新put一个替换后的key value键值对
     * @param map map
     * @param oldChar oldChar
     * @param newChar newChar
     */
    private static void replaceMapKeyName(Map<String, Object> map, String oldChar, String newChar){
        Map<String, Object> newMap = new HashMap<>();
        if(map == null) return;
        for(Map.Entry<String, Object> entry : map.entrySet()){
            String key = entry.getKey();
            key = key.replace(oldChar, newChar);
            newMap.put(key, entry.getValue());
        }
        map.putAll(newMap);
    }

    /***
     * 获取一个Class的所有自定义变量名称
     * @param c class类
     * @return String[]
     */
    private static String[] getObjectFieldNames(Class c){
        Field[] fields = c.getDeclaredFields();
        List<String> fieldNameList = new ArrayList<>();
        fieldNameList.add("id");
        for(Field field : fields){
            field.setAccessible(true);
            if(field.isAnnotationPresent(Ignore.class)){
                continue;
            }
            if(Modifier.isStatic(field.getModifiers())){
                continue;
            }
            if(field.isAnnotationPresent(FieldName.class)){
                FieldName fieldNameAnno = field.getAnnotation(FieldName.class);
                String fieldName = fieldNameAnno.value();
                //前面是本身的字段名，后面是注解里的字段名
                fieldNameList.add(field.getName() + ":" + fieldName);
            }else{
                fieldNameList.add(field.getName());
            }
        }
        int size = fieldNameList.size();
        String[] fieldNames = new String[size];
        for(int i = 0; i < size; i++){
            fieldNames[i] = fieldNameList.get(i);
        }
        return fieldNames;
    }

    /***
     * 校验类型是否为基本类型
     * @param classType 校验类型
     * @return
     */
    private static boolean checkType(Class classType){
        String[] types = {"Long", "Integer", "Double", "Float", "Character", "Boolean"};
        String typeName = classType.getSimpleName();
        for(String type : types) if(typeName.equals(type)) return true;
        return false;
    }


}
