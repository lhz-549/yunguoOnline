package com.hz.online.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PlantprodesDto {

    private Integer id;

    /**
     * 关联用户ID
     */
    private Integer userId;

    /**
     * 关联产品ID
     */
    private Integer productId;

    private Integer orderitemId;

    /**
     * 成长值
     */
    private Integer growupValue;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 可用状态 0 可以用，1 禁用
     */
    private String state;

    /**
     * 商品名称
     */
    private String pName;

    /**
     * 商品图片
     */
    private String pImage;

    /**
     * 商品描述
     */
    private String pDescription;

    private String growupandlife;

    private String dailymaintenance;

    private String categoryName;

    private String categoryDescription;


}
