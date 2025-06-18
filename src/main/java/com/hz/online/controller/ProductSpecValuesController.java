package com.hz.online.controller;


import com.hz.online.common.dto.ResponseResult;
import com.hz.online.entity.ProductSpecValuesDTO;
import com.hz.online.service.ProductSpecValuesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author haozi
 * @since 2024-07-03
 */
@RestController
@RequestMapping("/product-spec-values")
public class ProductSpecValuesController {
    @Autowired
    ProductSpecValuesService productSpecValuesService;

    @PostMapping("/selectSpecvalueBypid")
    public ResponseResult<List<ProductSpecValuesDTO>> selectSpecvalueBypid(@RequestParam String pid){
        return productSpecValuesService.selectSpecvalueBypid(pid);
    }
}
