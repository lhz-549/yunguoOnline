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
 * @since 2024-06-20
 */
@Data
public class Article implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 唯一标识文章项的ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    //文章类id
    private Integer articleCategory;
    /**
     * 浏览数
     */
    private Integer views;

    /**
     * 文章大图
     */
    private String articleImage;

    /**
     * 文章标题
     */
    private String articleTitle;

    /**
     * 文章内容
     */
    private String articleText;

    /**
     * 发表日期
     */
    private LocalDateTime postDate;

    /**
     * 禁用标志 0 用户发表，1 系统发表
     */
    private Boolean isUsersend;

    /**
     * 关联用户ID
     */
    private Integer userId;

    @Override
    public String toString() {
        return "Article{" +
            "id=" + id +
            ", views=" + views +
            ", articleImage=" + articleImage +
            ", articleTitle=" + articleTitle +
            ", articleText=" + articleText +
            ", postDate=" + postDate +
            ", isUsersend=" + isUsersend +
            ", userId=" + userId +
        "}";
    }
}
