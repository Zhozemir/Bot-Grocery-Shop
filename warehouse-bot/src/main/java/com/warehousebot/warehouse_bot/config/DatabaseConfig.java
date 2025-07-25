package com.warehousebot.warehouse_bot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

public class DatabaseConfig {

    @Bean
    public JdbcTemplate jdbcTemplate (DataSource ds){
        return new JdbcTemplate(ds);
    }

}
