package com.hz.online.mapper;

import com.hz.online.entity.AchInfoDTO;
import com.hz.online.entity.AchievementSuccessDTO;
import com.hz.online.entity.Achievesuccess;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 成就实现表 Mapper 接口
 * </p>
 *
 * @author haozi
 * @since 2024-06-26
 */
@Repository
public interface AchievesuccessMapper extends BaseMapper<Achievesuccess> {

    @Select("SELECT s.*, a.ach_cateid, g.achcate_name, a.ach_name, a.ach_image, a.ach_condition, a.ach_desc, a.ach_exp\n" +
            "FROM achievesuccess s\n" +
            "JOIN achievement a ON s.ach_id = a.ach_id\n" +
            "JOIN achcategory g ON g.achcate_id = a.ach_cateid\n" +
            "WHERE s.user_id = #{userid};")
    public List<AchievementSuccessDTO> achievesuccessService(String userid);

}
