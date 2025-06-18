package com.hz.online.entity;

import lombok.Data;

@Data
public class AddOrderRequestDTO {

    private String userid;
    /**
     * 关联商品ID
     */
    private String productId;

    /**
     * 商品数量
     */
    private String quantity;

    private String specvalueId;

    private String specvalueName;

    private String remark;
}
