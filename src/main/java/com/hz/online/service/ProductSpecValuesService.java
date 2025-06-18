package com.hz.online.service;

import com.hz.online.common.dto.ResponseResult;
import com.hz.online.entity.ProductSpecValues;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hz.online.entity.ProductSpecValuesDTO;
import com.hz.online.mapper.ProductSpecValuesMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author haozi
 * @since 2024-07-03
 */
@Service
public class ProductSpecValuesService {

    @Autowired
    ProductSpecValuesMapper productSpecValuesMapper;

    public ResponseResult<List<ProductSpecValuesDTO>> selectSpecvalueBypid(String pid){
        List<ProductSpecValuesDTO> productSpecValuesDTOS = productSpecValuesMapper.selectSpecvalueBypid(pid);
        return ResponseResult.success(productSpecValuesDTOS);
    }

}
