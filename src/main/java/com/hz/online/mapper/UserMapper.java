package com.hz.online.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hz.online.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hz.online.sqlbuilder.UserSqlProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author haozi
 * @since 2024-06-06
 */
@Repository
public interface UserMapper extends BaseMapper<User> {

    @SelectProvider(type = UserSqlProvider.class, method = "buildUserListSql")
    IPage<User> selectUserList(Page<?> page, @Param("username") String username);
}
