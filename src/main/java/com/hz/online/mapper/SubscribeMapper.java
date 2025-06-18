package com.hz.online.mapper;

import com.hz.online.entity.Subscribe;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hz.online.entity.SubscribePlantProDTO;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 预约表 Mapper 接口
 * </p>
 *
 * @author haozi
 * @since 2024-06-29
 */
@Repository
public interface SubscribeMapper extends BaseMapper<Subscribe> {

    @Select("SELECT\n" +
            "\ts.*,\n" +
            "\tp.user_id,\n" +
            "\tpro.p_name,\n" +
            "\tpro.p_image \n" +
            "FROM\n" +
            "\tsubscribe s\n" +
            "\tJOIN plant p ON p.id = s.plant_id\n" +
            "\tJOIN products pro ON pro.id = p.product_id \n" +
            "WHERE\n" +
            "\tp.state = 0 and p.user_id = #{userid} order by s.subcreate_time ")
    public List<SubscribePlantProDTO> findAllByUserid(String userid);
}
