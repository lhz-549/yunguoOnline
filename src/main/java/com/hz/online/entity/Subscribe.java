package com.hz.online.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * <p>
 * 预约表
 * </p>
 *
 * @author haozi
 * @since 2024-06-29
 */
@Data
public class Subscribe implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 唯一标识ID
     */
    @TableId(value = "sub_id", type = IdType.AUTO)
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

}
