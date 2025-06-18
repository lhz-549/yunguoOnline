package com.hz.online.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 文章表
 * </p>
 *
 * @author haozi
 * @since 2024-06-24
 */
@Data
public class Plant implements Serializable {

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

}
