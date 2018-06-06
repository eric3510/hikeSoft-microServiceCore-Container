package org.springframework.boot.container.core.pojo;

import lombok.Data;
import org.springframework.boot.container.core.annotation.FieldName;
import org.springframework.boot.container.core.annotation.TableName;
import org.springframework.boot.container.core.annotation.TreeLevel;
import org.springframework.boot.container.core.annotation.TreeParentId;

/**
 * @author eric E-mail:
 * @version 创建时间：2018/2/2 上午11:08
 * TreeDemoDO对象
 */
@Data
@TableName("tree_demo")
public class TreeDemoDO extends BaseDO{
    /***
     * 姓名
     */
    private String name;
    /***
     * 年龄
     */
    private Integer age;
    /***
     * 父节点
     */
    @TreeParentId
    @FieldName("parent_id")
    private Long parentId;
    /***
     * 层级
     */
    @TreeLevel
    private Integer level;

    public TreeDemoDO(){}

    public TreeDemoDO(String id, String name, Integer age, Long parentId, Integer level){
        this.setId(id);
        this.name = name;
        this.age = age;
        this.parentId = parentId;
        this.level = level;
    }
}
