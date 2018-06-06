package org.springframework.boot.container.core.pojo;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import lombok.Data;

/***
 * @author 王强 Email : eric3510@foxmail.com
 * @version 创建时间：2018/2/2
 * TreeVO
 */
@Data
public class TreeVO<T>{
    /***
     * 树的深度
     */
    private Integer depth;

    /***
     * 节点数量
     */
    private Integer NumberOfNodes;

    /***
     * 叶子节点
     */
    private List<Tree<T>> leafNode;

    /***
     * 普通树森林集合
     */
    private List<Tree<T>> treeList;


    /***
     * 二叉树森林集合
     */
    private List<BinaryTree<T>> binaryTreeList;

    /***
     *
     * @param args
     */
    public static void main(String[] args){
        Map<Long, TreeDemoDO> idMap = new HashMap<>();
        TreeDemoDO treeDemoDO = new TreeDemoDO();
        treeDemoDO.setAge(123);
        Long id = 1L;
        idMap.put(id, treeDemoDO);
        TreeDemoDO mapTreeDemoDO = idMap.get(id);
        mapTreeDemoDO.setAge(321);
        System.out.println(treeDemoDO.getAge());
    }

    /***
     * 装载普通树集合
     * @param list
     */
    public void setTreeList(List<Tree<T>> list){
        Map<Long, Tree<T>> idMap = new HashMap<>();
        this.setNodeMap(list, idMap);
        if(this.treeList == null){
            this.treeList = new ArrayList<>();
        }
        for(Tree<T> tree : list){
            Long parentId = tree.getParentId();//获取此节点的父节点
            Tree<T> parentNode = idMap.get(parentId);
            if(parentNode == null){//如果找不到此节点的父节点的话则说明此节点为根节点,将此节点装载到列表
                this.treeList.add(tree);
            }else{//如果不为空，则说明此节点在当前列表中存在父级节点,则将此节点插入父节点子节点队列中.
                System.out.println(JSONObject.toJSONString(parentNode.getChildren()));
                List<Tree<T>> treeList = parentNode.getChildren();
                if(treeList == null){
                    treeList = new ArrayList<>();
                    treeList.add(tree);
                    parentNode.setChildren(treeList);
                }else{
                    parentNode.getChildren().add(tree);

                }
            }
        }
    }

    private void setNodeMap(
            List<Tree<T>> list,
            Map<Long, Tree<T>> idMap
    ){
        for(Tree<T> tree : list){
            if(tree.getChildren() == null){
                tree.setChildren(new ArrayList<Tree<T>>());
            }
            idMap.put(tree.getId(), tree);
        }
    }

    private void setParentIdMap(
            List<TreeNodeAttrDTO<T>> list,
            Map<Integer, Tree<T>> parentIdMap
    ){
    }
}













