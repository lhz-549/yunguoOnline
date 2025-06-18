package com.hz.online.mapper;

import com.hz.online.entity.Cartitems;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 购物车项表 Mapper 接口
 * </p>
 *
 * @author haozi
 * @since 2024-06-17
 */
@Repository
@Mapper
public interface CartitemsMapper extends BaseMapper<Cartitems> {

    //@Delete("DELETE FROM cartitems WHERE cart_id = #{cartid} AND product_id IN (${pids})")
    @Delete("<script>" +
            "DELETE FROM cartitems WHERE  cartitem_id IN " +
            "<foreach item='item' index='index' collection='cartitemids' open='(' separator=',' close=')'>" +
            "#{item}" +
            "</foreach>" +
            "</script>")
    public int delcartitems(@Param("cartitemids") List<Integer> cartitemids);

    @Delete("DELETE FROM cartitems WHERE cartitem_id =#{cartitem_id} ")
    public int delcartitems2(@Param("cartitem_id") String cartitem_id);

    @Select("<script>" +
            "SELECT * FROM cartitems WHERE cartitem_id IN " +
            "<foreach item='item' index='index' collection='cartitemidstrA' open='(' separator=',' close=')'>" +
            "#{item}" +
            "</foreach>" +
            "</script>")
    public List<Cartitems> selcartitemsbystrs(@Param("cartitemidstrA") List<Integer> cartitemidstrA);
}
