package com.hz.online.mapper;

import com.hz.online.entity.FindPlantReqDTO;
import com.hz.online.entity.Plant;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hz.online.entity.PlantProductGrowDTO;
import com.hz.online.entity.PlantprodesDto;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 文章表 Mapper 接口
 * </p>
 *
 * @author haozi
 * @since 2024-06-24
 */
@Repository
public interface PlantMapper extends BaseMapper<Plant> {

    @Select("select t.*,p.p_name,p.p_image,p.p_description,p.growupandlife,p.dailymaintenance,c.category_name,c.category_description from plant t join products p on p.id = t.product_id join categories c on c.category_id = p.p_categoryid where t.user_id =  #{userid} and t.state = 0;")
    public List<PlantprodesDto> allplantinfobyuserid(@Param("userid") String userid);

    @Select("select t.*,p.p_name,p.p_image,p.p_description,p.growupandlife,p.dailymaintenance," +
            "c.category_name,c.category_description,g.* from plant t join products p on p.id = t.product_id " +
            "join categories c on c.category_id = p.p_categoryid join latest_plantgrow g on t.id = g.plant_id where t.user_id =  #{userid} and t.state = 0;")
    public List<PlantProductGrowDTO> allplantinfobyuseridGrow(@Param("userid") String userid);



}
