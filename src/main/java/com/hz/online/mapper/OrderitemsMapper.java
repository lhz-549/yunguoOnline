package com.hz.online.mapper;

import com.hz.online.entity.FindPlantReqDTO;
import com.hz.online.entity.OrderItemDto;
import com.hz.online.entity.Orderitems;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hz.online.entity.RGadoptDTO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 订单项表 Mapper 接口
 * </p>
 *
 * @author haozi
 * @since 2024-06-19
 */
@Repository
public interface OrderitemsMapper extends BaseMapper<Orderitems> {
    @Select("SELECT\n" +
            "\tt.*,\n" +
            "\tp.p_name,\n" +
            "\tp.p_price,\n" +
            "\tp.p_marketprice,\n" +
            "\tp.p_image,\n" +
            "\tp.p_categoryid,\n" +
            "\tp.p_description,\n" +
            "\tp.badge,\n" +
            "\tp.stock_quantity,\n" +
            "\tp.selled_quantity,\n" +
            "\tg.category_name,\n" +
            "\tg.category_description \n" +
            "FROM\n" +
            "\torderitems t\n" +
            "\tJOIN products p ON p.id = t.product_id\n" +
            "\tJOIN categories g ON p.p_categoryid = g.category_id \n" +
            "WHERE\n" +
            "\tt.user_id = #{userid} order by t.update_time desc;")
    List<OrderItemDto> allorderByuserid(@Param("userid") String userid);

    @Select("SELECT\n" +
            "\tt.*,\n" +
            "\tp.p_name,\n" +
            "\tp.p_price,\n" +
            "\tp.p_marketprice,\n" +
            "\tp.p_image,\n" +
            "\tp.p_categoryid,\n" +
            "\tp.p_description,\n" +
            "\tp.badge,\n" +
            "\tp.stock_quantity,\n" +
            "\tp.selled_quantity,\n" +
            "\tg.category_name,\n" +
            "\tg.category_description \n" +
            "FROM\n" +
            "\torderitems t\n" +
            "\tJOIN products p ON p.id = t.product_id\n" +
            "\tJOIN categories g ON p.p_categoryid = g.category_id \n" +
            "WHERE\n" +
            "\tt.user_id = #{userid} and p.p_name LIKE CONCAT('%', #{pname}, '%') order by t.update_time desc;")
    List<OrderItemDto> allorderByuseridandcondition(@Param("userid") String userid,@Param("pname") String pname);

    @Select("SELECT\n" +
            "\to.orderitem_id,\n" +
            "\to.quantity,\n" +
            "\to.used_num,\n" +
            "\to.order_num,\n" +
            "\to.specvalue_name,\n" +
            "\tp.p_name,\n" +
            "\tp.p_image\n" +
            "FROM\n" +
            "\torderitems o\n" +
            "\tJOIN products p ON p.id = o.product_id \n" +
            "\tJOIN categories c ON c.category_id = p.p_categoryid \n" +
            "WHERE\n" +
            "  o.orderitem_state in (2,3) and FIND_IN_SET( p.p_categoryid, '1,2,3,4,5,6,7,8' ) " +
            "and o.user_id = #{userid} and o.used_num > 0;")
    List<FindPlantReqDTO> findusedplant(String userid);

    @Select("SELECT\n" +
            "\tt.*,\n" +
            "\tp.p_name,\n" +
            "\tp.p_price,\n" +
            "\tp.p_marketprice,\n" +
            "\tp.p_image,\n" +
            "\tp.p_categoryid,\n" +
            "\tp.p_description,\n" +
            "\tp.badge,\n" +
            "\tp.stock_quantity,\n" +
            "\tp.selled_quantity,\n" +
            "\tg.category_name,\n" +
            "\tg.category_description \n" +
            "FROM\n" +
            "\torderitems t\n" +
            "\tJOIN products p ON p.id = t.product_id\n" +
            "\tJOIN categories g ON p.p_categoryid = g.category_id \n" +
            "WHERE\n" +
            "\tt.orderitem_id = #{orderid} ;")
    OrderItemDto selectorderinfobyorderid(@Param("orderid") String orderid);

    @Select("<script>" +
            "SELECT SUM(price) FROM orderitems WHERE orderitem_id IN " +
            "<foreach item='item' index='index' collection='orderitemidstrA' open='(' separator=',' close=')'>" +
            "#{item}" +
            "</foreach>" +
            "</script>")
    int selectallpricebyidstr(@Param("orderitemidstrA") List<Integer> orderitemidstrA);


    @Select("<script>" +
            "SELECT t.*, p.id, p.p_name,p.p_price,p.p_marketprice,p.p_image,p.p_categoryid,p.p_description,p.badge,p.stock_quantity,p.selled_quantity,g.category_name,g.category_description \n" +
            "FROM orderitems t\n" +
            "    JOIN products p ON p.id = t.product_id\n" +
            "    JOIN categories g ON p.p_categoryid = g.category_id \n" +
            "WHERE\n" +
            "    t.orderitem_id in" +
            "<foreach item='item' index='index' collection='orderitemidstrA' open='(' separator=',' close=')'>" +
            "#{item}" +
            "</foreach>" +
            "</script>")
    List<OrderItemDto> selectorderinfobyorderitemidstr(@Param("orderitemidstrA") List<Integer> orderitemidstrA);

    @Select({
            "SELECT o.product_id, \n" +
            "       SUM(o.used_num) as used_num,\n" +
            "       p.p_name,\n" +
            "       o.specvalue_name\n" +
            "FROM orderitems o\n" +
            "JOIN products p ON o.product_id = p.id\n" +
            "WHERE o.user_id = #{userid}\n" +
            "  AND p.p_categoryid IN (9, 10, 11, 12)\n" +
            "  AND o.orderitem_state = 2\n" +
            "GROUP BY o.product_id, o.specvalue_name,p.p_name;"
    })
    List<RGadoptDTO> selUsedRGAdoptNum(String userid);
}
