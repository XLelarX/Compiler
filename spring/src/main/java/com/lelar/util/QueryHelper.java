package com.lelar.util;

import com.lelar.database.annotation.Column;
import com.lelar.database.annotation.Table;
import com.lelar.database.entity.IdentifierEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
@Component
public class QueryHelper implements ApplicationContextAware {
    private final static String IGNORED_METHODS = "equals|toString|hashCode|canEqual|set.+";

    private static ApplicationContext context;
    private static JdbcTemplate jdbcTemplate;

    public static <T extends IdentifierEntity> void sendUpdateQuery(
        Class<?> clazz,
        T entity
    ) {
        sendUpdateQuery(clazz, entity, createUpdateQuery(clazz, entity.getId()));
    }

    public static <T> Long sendInsertQuery(
        Class<?> clazz,
        T entity
    ) {
        Long nextId = sendSeqQuery(clazz);

        sendUpdateQuery(clazz, entity, createInsertQuery(clazz, nextId));
        return nextId;
    }

    public static <T> List<T> sendSelectQuery(
        Class<?> clazz,
        Map<String, Object> params,
        RowMapper<T> rowMapper
    ) {
        return sendQuery(params, createSelectQuery(clazz, params), rowMapper);
    }

    public static <T> List<T> sendCustomQuery(
        String query,
        Map<String, Object> params,
        RowMapper<T> rowMapper
    ) {
        return sendQuery(params, query, rowMapper);
    }

    public static void sendCustomUpdateQuery(String query, Map<String, Object> params) {
        sendUpdateQuery(params, query);
    }

    public static void sendCustomUpdateQuery(String query, Object... params) {
        sendUpdateQuery(query, params);
    }

    public static <T> T sendCustomQuery(
        String query,
        Map<String, Object> params,
        ResultSetExtractor<T> extractor
    ) {
        return sendQuery(params, query, extractor);
    }

    public static <T> T sendSelectQuery(
        Class<?> clazz,
        Map<String, Object> params,
        ResultSetExtractor<T> extractor
    ) {
        return sendQuery(params, createSelectQuery(clazz, params), extractor);
    }

    private static <T> String createUpdateQuery(Class<T> clazz, Long idField) {
        StringBuilder builder = new StringBuilder("insert ");

        Table table = Optional.ofNullable(clazz.getAnnotation(Table.class)).orElseThrow();

        String fields = Arrays.stream(clazz.getDeclaredFields())
            .map(it -> it.getAnnotation(Column.class))
            .map(Column::value)
            .sorted(String::compareToIgnoreCase)
            .collect(Collectors.collectingAndThen(Collectors.joining(" = ?, "), it -> it += " = ?"));
        builder.append(table).append(" set ").append(fields);

        Optional.ofNullable(idField).ifPresent(
            it -> builder.append(" where id = ").append(it)
        );

        String query = builder.toString();
        log.info(query);
        return query;
    }


    private static <T> String createInsertQuery(Class<T> clazz, Long nextId) {
        StringBuilder builder = new StringBuilder("insert into ");

        Table table = Optional.ofNullable(clazz.getAnnotation(Table.class)).orElseThrow();

        List<String> fields = Arrays.stream(clazz.getDeclaredFields())
            .map(it -> it.getAnnotation(Column.class))
            .map(Column::value)
            .sorted(String::compareToIgnoreCase).collect(Collectors.toList());

        builder.append(table.name()).append("( ")
            .append(IdentifierEntity.Names.ID).append(", ")
            .append(String.join(", ", fields)).append(") values ( ")
            .append(nextId).append(", ")
            .append(fields.stream().map(it -> "?").collect(Collectors.joining(", "))).append(" )");

        String query = builder.toString();
        log.info(query);
        return query;
    }

    private static <T> String createSelectQuery(Class<T> clazz, Map<String, Object> params) {
        StringBuilder builder = new StringBuilder("select * from ");

        Table table = Optional.ofNullable(clazz.getAnnotation(Table.class)).orElseThrow();
        builder.append(table.name());

        if (!CollectionUtils.isEmpty(params)) {
            builder.append(" where ")
                .append(params.keySet().stream()
                    .map(it -> it + " = ?")
                    .collect(Collectors.joining(", ")));
        }

        String query = builder.toString();
        log.info(query);
        return query;
    }

    private static <T> List<T> sendQuery(
        Map<String, Object> params,
        String query,
        RowMapper<T> rowMapper
    ) {
        if (CollectionUtils.isEmpty(params)) {
            log.info("params is empty");
            return getJdbcTemplate().query(query, rowMapper);
        }

        log.info(params.toString());
        return getJdbcTemplate().query(query, rowMapper, params.values().toArray());
    }

    private static <T> T sendQuery(
        Map<String, Object> params,
        String query,
        ResultSetExtractor<T> rowMapper
    ) {
        log.info(params.toString());
        return getJdbcTemplate().query(query, rowMapper, params.values().toArray());
    }

    private static void sendUpdateQuery(
        Map<String, Object> params,
        String query
    ) {
        log.info(params.toString());
        getJdbcTemplate().update(query, params.values().toArray());
    }

    private static void sendUpdateQuery(
        String query,
        Object... params
    ) {
        log.info(Arrays.toString(params));
        getJdbcTemplate().update(query, params);
    }

    private static <T> void sendUpdateQuery(
        Class<?> clazz,
        T entity,
        String query
    ) {
        Object[] params = Arrays.stream(clazz.getDeclaredMethods())
            .filter(it -> !Pattern.compile(IGNORED_METHODS).matcher(it.getName()).find())
            .sorted((first, second) -> first.getName().compareToIgnoreCase(second.getName()))
            .map(it -> {
                try {
                    return it.invoke(entity);
                } catch (Exception e) {
                    return null;
                }
            }).toArray();

        log.info(Arrays.toString(params));

        getJdbcTemplate().update(query, params);
    }


    public static <T> Long sendSeqQuery(
        Class<?> clazz
    ) {
        Table table = Optional.ofNullable(clazz.getAnnotation(Table.class)).orElseThrow();

        String currentValueStr = "cur_val";
        String query = "select next value for " + table.sequence() + " as " + currentValueStr;

        return getJdbcTemplate().query(query, (it) -> {
            if (!it.next()) {
                return null;
            }

            return it.getLong(currentValueStr);
        });
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

    private static JdbcTemplate getJdbcTemplate() {
        if (jdbcTemplate == null) {
            jdbcTemplate = context.getBean(JdbcTemplate.class);
        }
        return jdbcTemplate;
    }
}
