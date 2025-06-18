package com.hz.online.mapper;

import com.hz.online.entity.ProductSpecValues;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hz.online.entity.ProductSpecValuesDTO;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author haozi
 * @since 2024-07-03
 */
@Repository
public interface ProductSpecValuesMapper extends BaseMapper<ProductSpecValues> {

    @Select("SELECT\n" +
            "\tv.*,\n" +
            "\ts.spec_name \n" +
            "FROM\n" +
            "\tproduct_spec_values v\n" +
            "\tJOIN product_specs s ON s.id = v.spec_id \n" +
            "WHERE\n" +
            "\tv.product_id = #{pid} \n" +
            "ORDER BY\n" +
            "\tv.id;")
    public List<ProductSpecValuesDTO> selectSpecvalueBypid(String pid);

    @Select("<script>" +
            "select SUM(v.spec_price) as specprice_total " +
            "from product_spec_values v " +
            "where v.id in " +
            "<foreach item='item' index='index' collection='list' open='(' separator=',' close=')'>" +
            "#{item}" +
            "</foreach>" +
            "</script>")
    BigDecimal selspecpricetotal(List<Integer> svid);
}
