package com.hz.online.mapper;

import com.hz.online.entity.Maintainrecord;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hz.online.entity.MaintainrecordProductDto;
import com.hz.online.sqlbuilder.MaintainRecordSqlBuilder;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 养护记录表 Mapper 接口
 * </p>
 *
 * @author haozi
 * @since 2024-06-24
 */
@Repository
public interface MaintainrecordMapper extends BaseMapper<Maintainrecord> {

    @Select("select m.*,p.p_name from maintainrecord m join products p on p.id = m.product_id where m.user_id=#{userid} and m.operation != '能量球' order by  execute_time desc;")
    List<MaintainrecordProductDto> allRecordByUseridwithpname(String userid);

    @Select("select m.*,p.p_name from maintainrecord m join products p on p.id = m.product_id where m.user_id=#{userid} and m.operation = '能量球' order by  execute_time desc ;")
    List<MaintainrecordProductDto> allRecordByUseridwithpname2(String userid);

    @SelectProvider(type = MaintainRecordSqlBuilder.class, method = "buildGetMaintainRecordsQuery")
    List<MaintainrecordProductDto> allRecordByuseridandpnameandoperation(@Param("userid") String userid,
                                                                         @Param("pname") String pname,
                                                                         @Param("operate") String operate);
}
