package com.hz.online.controller;


import com.hz.online.common.dto.ResponseResult;
import com.hz.online.entity.ProductCategoryDTO;
import com.hz.online.entity.Products;
import com.hz.online.mapper.ProductsMapper;
import com.hz.online.service.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * <p>
 * 商品表 前端控制器
 * </p>
 *
 * @author haozi
 * @since 2024-06-17
 */
@RestController
@RequestMapping("/products")
public class ProductsController {
    @Autowired
    ProductsService productsService;

    @PostMapping("/allprolist")
    public ResponseResult<List<Products>> allprolist(){
        return ResponseResult.success(productsService.allprolist());
    }
    @PostMapping("/allprolist2")
    public ResponseResult<List<Products>> allprolist2(String id){
        return ResponseResult.success(productsService.allprolistexceptid(id));
    }
    @PostMapping("/allprolistandtype")
    public ResponseResult<List<ProductCategoryDTO>> allprolistandtype(){
        return ResponseResult.success(productsService.allprolistandtype());
    }
    @PostMapping("/allprolistandtype2")
    public ResponseResult<List<ProductCategoryDTO>> allprolistandtype2(String id ){
        return ResponseResult.success(productsService.allprolistandtypeexceptid(id));
    }

    @PostMapping("/allprolistandtypebyid")
    public ResponseResult<List<ProductCategoryDTO>> allprolistandtypebyid(@RequestParam String id){
        return ResponseResult.success(productsService.allprolistandtypebyid(id));
    }

    @PostMapping("/searchthing")
    public ResponseResult<List<ProductCategoryDTO>> searchthing(@RequestParam String keyword){
        return ResponseResult.success(productsService.searchthing(keyword));
    }
}
