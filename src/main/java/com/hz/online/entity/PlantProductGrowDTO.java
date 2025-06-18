package com.hz.online.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PlantProductGrowDTO {

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

    private Integer growId;

    /**
     * 关联种植ID
     */
    private Integer plantId;

    /**
     * 阶段
     */
    private String stage;

    /**
     * 阶段描述
     */
    private String stageDesc;

    /**
     * 是否健康
     */
    private String isHealth;

    /**
     * 对应处理或提示
     */
    private String adopt;

    /**
     * 创建时间
     */
    private LocalDateTime growTime;

    /**
     * 状态 1：系统每周1次处理 2： 预约处理
     */
    private Boolean gstate;

}
