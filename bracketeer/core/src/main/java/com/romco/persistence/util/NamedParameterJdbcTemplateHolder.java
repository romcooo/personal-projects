package com.romco.persistence.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
public class NamedParameterJdbcTemplateHolder {
    private static NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public static NamedParameterJdbcTemplate get() {
        return namedParameterJdbcTemplate;
    }

    @Autowired
    public void setDataSource(DataSource dataSource) {
        namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        System.out.println("qweasdqwe");
    }
}
