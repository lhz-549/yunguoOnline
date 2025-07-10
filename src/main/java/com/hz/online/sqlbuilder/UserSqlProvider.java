package com.hz.online.sqlbuilder;

import java.util.Map;

public class UserSqlProvider {

    public String buildUserListSql(Map<String, Object> params) {
        String username = (String) params.get("username");

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * FROM user WHERE 1=1 ");
        if (username != null && !username.isEmpty()) {
            sql.append("AND name LIKE CONCAT('%', #{username}, '%') ");
        }
        return sql.toString();
    }
}
