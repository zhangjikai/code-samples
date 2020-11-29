package com.zhangjikai.sample.jpa.dto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * @author Jikai Zhang
 * @date 2020-11-28
 */
@Repository
public class UserRelationDAO {
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;
    
    public void save(UserRelation userRelation) {
        String sql = "insert into user_relation (user_id, fan_id, timestamp) values (:userId, :fanId, :timestamp)";
        MapSqlParameterSource sqlParameter = new MapSqlParameterSource()
                .addValue("userId", userRelation.getUserId())
                .addValue("fanId", userRelation.getFanId())
                .addValue("timestamp", userRelation.getTimestamp());
        jdbcTemplate.update(sql, sqlParameter);
        
    }
}
