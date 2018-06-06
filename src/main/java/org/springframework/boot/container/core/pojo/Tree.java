package org.springframework.boot.container.core.pojo;

import lombok.Data;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;

/***
 * @author 王强 Email : eric3510@foxmail.com
 * @version 创建时间：2018/2/2
 * Tree
 */
@Data
public class Tree<T> extends HashMap<String, Object>{
    private static final String CHILDREN_T = "children";

    private static final String ID_T = "id";

    private static final String PARENT_ID = "parentId";

    private static final String TREE_LEVEL_T = "treeLevel";

//    /***
//     * 子节点
//     */
//    private List<Tree<T>> children;
//
//    private Long id;
//
//    private Long parentId;
//
//    private Integer treeLevel;

    public Tree(Long id, Long parentId, T node){
        this.setId(id);
        this.setParentId(parentId);
        this.setNode(node);
    }

    public void setChildren(List<Tree<T>> children){
        this.put(CHILDREN_T, children);
    }

    public List<Tree<T>> getChildren(){
        return (List<Tree<T>>) this.get(CHILDREN_T);
    }

    public void setId(Long id){
        this.put(ID_T, id);
    }

    public Long getId(){
        return (Long) this.get(ID_T);
    }

    public void setParentId(Long parentId){
        this.put(PARENT_ID, parentId);
    }

    public Long getParentId(){
        return (Long) this.get(PARENT_ID);
    }

    public Tree(){}

//   /***
//    * 获取节点父子属性对象
//    * @param node
//    * @param annotationDTO
//    * @return
//    * @throws Exception
//    */
//   public TreeNodeAttrDTO getNodeAttr(T node, AnnotationDTO annotationDTO){
//      Class nodeClass = node.getClass();
//      String treeLevelFieldName = annotationDTO.getTreeLevelFieldName();
//      String treeParentName = annotationDTO.getTreeParentIdFieldName();
//      try{
//         Field treeLevelField = nodeClass.getDeclaredField(treeLevelFieldName);
//         treeLevelField.setAccessible(true);
//         Integer treeLevel = (Integer) treeLevelField.get(node);
//
//         Field treeParentNameField = nodeClass.getDeclaredField(treeParentName);
//         treeParentNameField.setAccessible(true);
//         Long parentId = (Long) treeParentNameField.get(node);
//
//         Field treeIdField = nodeClass.getDeclaredField("id");
//         treeIdField.setAccessible(true);
//         Long id = (Long) treeIdField.get(node);
//         return new TreeNodeAttrDTO(id, parentId, treeLevel, node);
//      }catch(Exception e){
//         e.printStackTrace();
//      }
//      return null;
//   }


    /***
     * 获取节点父子属性对象
     * @param node
     * @return
     * @throws Exception
     */
    public void setNodeAttr(T node){
        Class nodeClass = node.getClass();
        try{
            Field treeIdField = nodeClass.getDeclaredField("id");
            treeIdField.setAccessible(true);
            this.setId((Long) treeIdField.get(node));
            this.setNode(node);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void setNode(T node){
        Class c = node.getClass();
        Field[] fields = c.getDeclaredFields();
        for(Field field : fields){
            String name = field.getName();
            try{
                field.setAccessible(true);
                this.put(name, field.get(node));
            }catch(IllegalAccessException e){
                e.printStackTrace();
            }
        }
    }
}
















