package com.hz.online.entity;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class SubscribePlantProDTO {

    private Integer subId;

    /**
     * 关联种植表ID
     */
    private Integer plantId;

    /**
     * 预约类型
     */
    private String subCate;

    /**
     * 预约日期
     */
    private LocalDate subDate;

    /**
     * 预约时间段
     */
    private String subTime;

    /**
     * 预约处理
     */
    private String subAdopt;

    /**
     * 预约备注
     */
    private String subRemarks;

    /**
     * 表单创建时间
     */
    private LocalDateTime subcreateTime;

    /**
     * 表单更新时间
     */
    private LocalDateTime subupdateTime;

    /**
     * 预约标志 0 禁用 1 已提交 2 已处理 3 已被拒
     */
    private Integer subState;

    private Integer userId;

    private String pName;

    private String pImage;
}
