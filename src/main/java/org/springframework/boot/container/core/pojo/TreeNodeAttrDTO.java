package org.springframework.boot.container.core.pojo;

/***
 * @author 王强 Email : eric3510@foxmail.com
 * @version 创建时间：2018/2/2
 * TreeNodeAttrDTO
 */

public class TreeNodeAttrDTO<T>{
    private Long id;
    private Long parentId;
    private Integer treeLevel;
    private T node;
    public TreeNodeAttrDTO(Long id, Long parentId, Integer treeLevel, T node){
        this.id = id;
        this.parentId = parentId;
        this.treeLevel = treeLevel;
        this.node = node;
    }
}
