package org.springframework.boot.container.core.mysql;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author 王强
 * @version 创建时间：2017/08/18 11:39
 * SqlTemplate
 **/
@Data
public class SqlTemplate{

    public static final String PARAM_MAP_T = "{paramMap.";
    /***
     * sql语句
     */
    private String sql;

    /**
     * 主键
     */
    private Object id;

    /***
     * 表名
     */
    private String tableName;

    /***
     * 参数map集合
     */
    private Map<String, Object> paramMap;

    /***
     * 保存时的字段名称队列
     */
    private String[] saveFieldNames;

    /***
     * 二维数组
     */
    private List<Map<String, Object>[]> saveValues;

    /***
     * 要更新的字段名和字段值
     */
    private List<KeyValueTemplate> updateFieldList;

    /***
     * Object
     */
    private Object object;

    /***
     * object集合
     */
    private List<? extends Object> objectList;

    /***
     * map集合
     */
    private Map<String, Object> map;

    /***
     * List Map 数组集合
     */
    private List<Map<String, Object>> listMap;

    /**
     * key value 键值对对象
     */
    private KeyValueTemplate keyValueTemplate;
}
