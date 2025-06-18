package com.hz.online.controller;


import com.hz.online.entity.DicDistrict;
import com.hz.online.service.DicDistrictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 地区编码表 前端控制器
 * </p>
 *
 * @author haozi
 * @since 2024-06-07
 */
@RestController
@RequestMapping("/dic-district")
public class DicDistrictController {
    @Autowired
    private DicDistrictService dicDistrictService;

    @GetMapping("/hierarchical")
    public ResponseEntity<List<Map<String, Object>>> getHierarchicalDistricts() {
        List<Map<String, Object>> hierarchicalDistricts = dicDistrictService.getHierarchicalDistricts();
        return ResponseEntity.ok(hierarchicalDistricts);
    }

}
