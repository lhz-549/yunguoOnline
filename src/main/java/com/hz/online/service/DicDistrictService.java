package com.hz.online.service;

import com.hz.online.entity.DicDistrict;
import com.hz.online.mapper.DicDistrictMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;



/**
 * <p>
 * 地区编码表 服务类
 * </p>
 *
 * @author haozi
 * @since 2024-06-07
 */
@Service
public class DicDistrictService{
    @Autowired
    private DicDistrictMapper dicDistrictMapper;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<DicDistrict> getAllDistricts() {
        return dicDistrictMapper.selectList(null);
    }

    public List<Map<String, Object>> getHierarchicalDistricts() {
        List<DicDistrict> districts = getAllDistricts();
        Map<String, List<DicDistrict>> districtMap = districts.stream()
                .collect(Collectors.groupingBy(DicDistrict::getParentAddressCode));

        return buildHierarchy(districtMap, "0");
    }

    private List<Map<String, Object>> buildHierarchy(Map<String, List<DicDistrict>> districtMap, String parentCode) {
        List<Map<String, Object>> result = new ArrayList<>();
        List<DicDistrict> children = districtMap.get(parentCode);

        if (children != null) {
            for (DicDistrict child : children) {
                Map<String, Object> node = new HashMap<>();
                node.put("addressCode", child.getAddressCode());
                node.put("addressName", child.getAddressName());
                node.put("children", buildHierarchy(districtMap, child.getAddressCode()));
                result.add(node);
            }
        }
        return result;
    }

    public List<DicDistrict> getHierarchicalDistrictsByCTE() {
        String sql = "with recursive sub_districts as (\n" +
                "    select address_code, address_name, parent_address_code\n" +
                "    from dic_district\n" +
                "    where parent_address_code = '0'\n" +
                "    union all\n" +
                "    select d.address_code, d.address_name, d.parent_address_code\n" +
                "    from dic_district d\n" +
                "    inner join sub_districts s on s.address_code = d.parent_address_code\n" +
                ")\n" +
                "select * from sub_districts;";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(DicDistrict.class));
    }
}
