package org.springframework.boot.container.core.pojo;

import lombok.Data;

import java.util.List;

/**
 * @author 王强
 * @version 创建时间：2017/9/21
 * PagingVO
 **/
@Data
public class PagingVO<T> extends BaseVO{
    /***
     * 总条数
     */
    private Integer total;

    /***
     * 总页数
     */
    private Integer pageTotal;

    /***
     * 当前页
     */
    private Integer currentPage;

    /***
     * 内容
     */
    private List<T> list;

    /***
     * 获取总页数
     * @param total 总条数
     * @param pageSize 每页大小
     * @return int
     */
    public static int getPageTotal(int total, int pageSize){
        return (total % pageSize) > 0 ? total / pageSize + 1 : total / pageSize;
    }
}
