package com.hz.online.mapper;

import com.hz.online.entity.ProductCategoryDTO;
import com.hz.online.entity.Products;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 商品表 Mapper 接口
 * </p>
 *
 * @author haozi
 * @since 2024-06-17
 */
@Repository
@Mapper
public interface ProductsMapper extends BaseMapper<Products> {
    @Select("select p.*, t.category_name,t.category_description from products p join categories t on t.category_id = p.p_categoryid;")
    public List<ProductCategoryDTO> allprolistandtype();

    @Select("select p.*, t.category_name,t.category_description from products p join categories t on t.category_id = p.p_categoryid where id != #{id};")
    List<ProductCategoryDTO> allprolistandtypeexceptid(String id);

    @Select("select p.*, t.category_name,t.category_description from products p join categories t on t.category_id = p.p_categoryid where id = #{id};")
    List<ProductCategoryDTO> allprolistandtypebyid(String id);

    @Select("select p.*, t.category_name,t.category_description from products p join categories t on t.category_id = p.p_categoryid where p.p_name LIKE CONCAT('%', #{keyword}, '%');")
    List<ProductCategoryDTO> searchAllByPName(String keyword);
}
