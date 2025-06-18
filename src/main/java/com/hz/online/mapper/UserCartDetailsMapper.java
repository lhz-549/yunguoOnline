package com.hz.online.mapper;

import com.hz.online.entity.UserCartDetails;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * VIEW Mapper 接口
 * </p>
 *
 * @author haozi
 * @since 2024-06-14
 */
@Repository
public interface UserCartDetailsMapper extends BaseMapper<UserCartDetails> {

    @Select("SELECT \n" +
            "    ucd.user_id,\n" +
            "    ucd.cartitem_id,\n" +
            "    ucd.specvalue_name,\n" +
            "    ucd.create_time,\n" +
            "    ucd.update_time,\n" +
            "    ucd.product_id,\n" +
            "    ucd.specvalue_id,\n" +
            "    ucd.p_name,\n" +
            "    ucd.p_image,\n" +
            "    ucd.quantity,\n" +
            "    ucd.p_price,\n" +
            "    ucd.total_price,\n" +
            "    (SELECT SUM(v.spec_price) FROM product_spec_values v WHERE FIND_IN_SET(v.id, ucd.specvalue_id)) + ucd.p_price AS unit_price\n" +
            "FROM \n" +
            "    user_cart_details ucd where ucd.user_id = #{userid};")
    List<UserCartDetails> cartdetal(String userid);

    @Select("SELECT \n" +
            "    ucd.user_id,\n" +
            "    ucd.cartitem_id,\n" +
            "    ucd.specvalue_name,\n" +
            "    ucd.create_time,\n" +
            "    ucd.update_time,\n" +
            "    ucd.product_id,\n" +
            "    ucd.specvalue_id,\n" +
            "    ucd.p_name,\n" +
            "    ucd.p_image,\n" +
            "    ucd.quantity,\n" +
            "    ucd.p_price,\n" +
            "    ucd.total_price,\n" +
            "    (SELECT SUM(v.spec_price) FROM product_spec_values v WHERE FIND_IN_SET(v.id, ucd.specvalue_id)) + ucd.p_price AS unit_price\n" +
            "FROM \n" +
            "    user_cart_details ucd where ucd.cartitem_id = #{cartitemid};")
    UserCartDetails cartdetalbycartitemid(String cartitemid);

    @Select("SELECT\n" +
            "\tucd.user_id,\n" +
            "\tucd.cartitem_id,\n" +
            "\tucd.create_time,\n" +
            "\tucd.update_time,\n" +
            "\tucd.product_id,\n" +
            "\tucd.specvalue_id,\n" +
            "\tucd.specvalue_name,\n" +
            "\tucd.p_name,\n" +
            "\tucd.p_image,\n" +
            "\tucd.quantity,\n" +
            "\tucd.p_price,\n" +
            "\tucd.total_price,\n" +
            "\t( SELECT SUM( v.spec_price ) FROM product_spec_values v WHERE FIND_IN_SET( v.id, ucd.specvalue_id ) ) + ucd.p_price AS unit_price \n" +
            "FROM\n" +
            "\t(\n" +
            "SELECT\n" +
            "\tc.user_id,\n" +
            "\tci.cartitem_id,\n" +
            "\tci.create_time,\n" +
            "\tci.update_time,\n" +
            "\tci.product_id,\n" +
            "\tci.specvalue_id,\n" +
            "\tci.specvalue_name,\n" +
            "\tp.p_name,\n" +
            "\tp.p_image,\n" +
            "\tci.quantity,\n" +
            "\tp.p_price,\n" +
            "\tci.price AS total_price \n" +
            "FROM\n" +
            "\tcart c\n" +
            "\tJOIN cartitems ci ON c.cart_id = ci.cart_id\n" +
            "\tJOIN products p ON ci.product_id = p.id \n" +
            "WHERE\n" +
            "\tc.state = 0 \n" +
            "ORDER BY\n" +
            "\tci.update_time DESC \n" +
            "\t) AS ucd \n" +
            "WHERE\n" +
            "\tucd.user_id = #{userid}")
    List<UserCartDetails> cartdetal2(String userid);

    @Select("<script>" +
            "SELECT ucd.user_id, ucd.cartitem_id, ucd.create_time, ucd.update_time, ucd.product_id, ucd.specvalue_id, ucd.specvalue_name, ucd.p_name, ucd.p_image, ucd.quantity, ucd.p_price, ucd.total_price, " +
            "(SELECT SUM(v.spec_price) FROM product_spec_values v WHERE FIND_IN_SET(v.id, ucd.specvalue_id)) + ucd.p_price AS unit_price " +
            "FROM (" +
            "SELECT c.user_id, ci.cartitem_id, ci.create_time, ci.update_time, ci.product_id, ci.specvalue_id, ci.specvalue_name, p.p_name, p.p_image, ci.quantity, p.p_price, ci.price AS total_price " +
            "FROM cart c JOIN cartitems ci ON c.cart_id = ci.cart_id JOIN products p ON ci.product_id = p.id " +
            "WHERE c.state = 0 ORDER BY ci.update_time DESC) AS ucd " +
            "WHERE ucd.user_id = #{userid} AND ucd.cartitem_id IN " +
            "<foreach item='cartItemId' index='index' collection='cartItemIdsList' open='(' separator=',' close=')'>" +
            "#{cartItemId}" +
            "</foreach>" +
            "</script>")
    List<UserCartDetails> selectcartdetalbyuseridandcartitemidstr(@Param("userid") String userid,@Param("cartItemIdsList") List<Integer> cartItemIdsList);

}
