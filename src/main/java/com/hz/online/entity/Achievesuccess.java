package com.hz.online.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 成就实现表
 * </p>
 *
 * @author haozi
 * @since 2024-06-26
 */
@Data
public class Achievesuccess implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 唯一标识ID
     */
    @TableId(value = "as_id", type = IdType.AUTO)
    private Integer asId;

    /**
     * 关联用户ID
     */
    private Integer userId;

    /**
     * 关联成就表ID
     */
    private Integer achId;

    /**
     * 成就实现时间
     */
    private LocalDateTime achieveTime;

}
