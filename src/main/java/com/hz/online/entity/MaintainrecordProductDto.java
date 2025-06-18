package com.hz.online.entity;

import lombok.Data;

import java.time.LocalDateTime;


@Data
public class MaintainrecordProductDto {

    private Integer id;

    /**
     * 关联用户ID
     */
    private Integer userId;

    /**
     * 关联产品ID
     */
    private Integer productId;

    private Integer plantId;

    /**
     * 操作
     */
    private String operation;

    /**
     * 操作值
     */
    private String operationValue;

    /**
     * 当前成长值
     */
    private Integer currentValue;

    /**
     * 执行时间
     */
    private LocalDateTime executeTime;

    private String pName;;
}
