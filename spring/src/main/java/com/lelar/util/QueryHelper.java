package com.lelar.util;

import com.lelar.database.annotation.Sequence;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.util.List;

@Slf4j
@Component
public class QueryHelper implements ApplicationContextAware {

    private static ApplicationContext context;
    private static JdbcOperations jdbcTemplate;

    public static Long getSequenceNextValue(Class<?> clazz) {
        PreparedStatementCreatorFactory nextValueRequest = new PreparedStatementCreatorFactory(
            "SELECT NEXT VALUE FOR %s AS result".formatted(clazz.getAnnotation(Sequence.class).value())
        );
        PreparedStatementCreator preparedStatementCreator = nextValueRequest.newPreparedStatementCreator(List.of());
        return getJdbcTemplate().execute(preparedStatementCreator, it -> {
            ResultSet result = it.executeQuery();
            if (!result.next()) {
                return null;
            }

            return result.getLong("result");
        });
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

    private static JdbcOperations getJdbcTemplate() {
        if (jdbcTemplate == null) {
            jdbcTemplate = context.getBean(JdbcTemplate.class);
        }
        return jdbcTemplate;
    }
}
