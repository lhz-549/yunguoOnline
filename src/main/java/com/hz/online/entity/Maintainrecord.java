package com.hz.online.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 养护记录表
 * </p>
 *
 * @author haozi
 * @since 2024-06-24
 */
@Data
public class Maintainrecord implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 唯一标识文章项的ID
     */
    @TableId(value = "id", type = IdType.AUTO)
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

}
