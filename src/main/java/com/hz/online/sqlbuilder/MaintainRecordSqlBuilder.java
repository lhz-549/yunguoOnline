package com.hz.online.sqlbuilder;

import java.util.Map;

public class MaintainRecordSqlBuilder {
    public String buildGetMaintainRecordsQuery(Map<String, Object> params) {
        StringBuilder sql = new StringBuilder();
        String nlq="能量球";
        sql.append("SELECT m.*, p.p_name ");
        sql.append("FROM maintainrecord m ");
        sql.append("JOIN products p ON p.id = m.product_id ");
        sql.append("WHERE m.user_id = #{userid} ");

        if (params.get("pname") != null && !params.get("pname").toString().isEmpty()) {
            sql.append("AND p.p_name LIKE CONCAT('%', #{pname}, '%') ");
        }

        if (params.get("operate") != null && !params.get("operate").toString().isEmpty()) {
            sql.append("AND m.operation LIKE CONCAT('%', #{operate}, '%') ");
        }
        sql.append("AND m.operation != '").append(nlq).append("' ");
        sql.append(" order by m.execute_time desc");
        return sql.toString();
    }
}
