package org.springframework.boot.container.core.pojo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 王强
 * @version 创建时间：2017/09
 * BaseDTO
 **/
@Data
public class BaseDTO extends Paging implements Serializable{
//    @ApiModelProperty(value = "id")
    private String id;
}
