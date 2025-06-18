package com.hz.online.mapper;

import com.hz.online.entity.AchInfoDTO;
import com.hz.online.entity.Achievement;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 成就表 Mapper 接口
 * </p>
 *
 * @author haozi
 * @since 2024-06-26
 */
@Repository
public interface AchievementMapper extends BaseMapper<Achievement> {

    @Select("SELECT a.ach_id, a.ach_cateid, g.achcate_name, a.ach_name, a.ach_image, a.ach_condition, a.ach_desc, a.ach_exp\n" +
            "FROM achievement a\n" +
            "JOIN achcategory g ON g.achcate_id = a.ach_cateid order by a.ach_cateid , a.ach_id;")
    public List<AchInfoDTO> selallach();

}
