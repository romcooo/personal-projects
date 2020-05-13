package com.romco.bracketeer.persistence.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Slf4j
@Component
public class NamedParameterJdbcTemplateHolder {
    private static NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public static NamedParameterJdbcTemplate get(DataSource dataSource) {
        if (namedParameterJdbcTemplate == null) {
            namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        }
        log.debug("in get, template: {}", namedParameterJdbcTemplate);
        return namedParameterJdbcTemplate;
    }
    
}
