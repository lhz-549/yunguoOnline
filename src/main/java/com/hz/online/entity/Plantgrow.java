package com.hz.online.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 种种生长情况表
 * </p>
 *
 * @author haozi
 * @since 2024-06-25
 */
@Data
public class Plantgrow implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 唯一标识文章项的ID
     */
    @TableId(value = "id", type = IdType.AUTO)
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
