package com.hz.online.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hz.online.common.dto.ResponseResult;
import com.hz.online.entity.ProductCategoryDTO;
import com.hz.online.entity.Products;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hz.online.mapper.ProductsMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 商品表 服务类
 * </p>
 *
 * @author haozi
 * @since 2024-06-17
 */
@Service
@Slf4j
public class ProductsService {
    @Autowired
    ProductsMapper productsMapper;

    public List<Products> allprolist(){
        List<Products> products = productsMapper.selectList(null);
        return products;
    }
    public List<Products> allprolistexceptid(String id){
        QueryWrapper<Products> queryWrapper=new QueryWrapper();
        queryWrapper.ne("id",id);
        List<Products> products = productsMapper.selectList(queryWrapper);
        return products;
    }
    public List<ProductCategoryDTO> allprolistandtype(){
        List<ProductCategoryDTO> list = productsMapper.allprolistandtype();
        return list;
    }
    public List<ProductCategoryDTO> allprolistandtypeexceptid(String id){
        List<ProductCategoryDTO> list = productsMapper.allprolistandtypeexceptid(id);
        return list;
    }

    public List<ProductCategoryDTO> allprolistandtypebyid(String id){
        List<ProductCategoryDTO> list = productsMapper.allprolistandtypebyid(id);
        return list;
    }

    public List<ProductCategoryDTO> searchthing(String keyword){
        List<ProductCategoryDTO> productCategoryDTOS = productsMapper.searchAllByPName(keyword);
        return productCategoryDTOS;
    }

}
