package com.hz.online.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 购物车表
 * </p>
 *
 * @author haozi
 * @since 2024-06-17
 */
@Data
public class Cart implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 唯一标识购物车的ID
     */
    @TableId(value = "cart_id", type = IdType.AUTO)
    private Integer cartId;

    /**
     * 关联用户ID
     */
    private Integer userId;

    /**
     * 创建时间
     */
    private LocalDateTime createDate;

    /**
     * 购物车状态   0:可用,1:禁用
     */
    private String state;

    public Integer getCartId() {
        return cartId;
    }

    public void setCartId(Integer cartId) {
        this.cartId = cartId;
    }
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "Cart{" +
            "cartId=" + cartId +
            ", userId=" + userId +
            ", createDate=" + createDate +
            ", state=" + state +
        "}";
    }
}
