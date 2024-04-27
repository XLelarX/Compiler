package com.lelar.util;

import com.lelar.database.annotation.Column;
import com.lelar.database.annotation.Table;
import com.lelar.database.entity.IdentifierEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
@Component
public class QueryHelper implements ApplicationContextAware {
    private final static String IGNORED_METHODS = "equals|toString|hashCode|canEqual|set.+";

    private static ApplicationContext context;

    private static <T> String createUpdateQuery(Class<T> clazz, Long idField) {
        StringBuilder builder = new StringBuilder("insert ");

        Optional<String> table = Optional.ofNullable(clazz.getAnnotation(Table.class)).map(Table::name);
        if (table.isEmpty()) {
            return "";
        }

        String fields = Arrays.stream(clazz.getDeclaredFields())
            .map(it -> it.getAnnotation(Column.class))
            .map(Column::value)
            .sorted(String::compareToIgnoreCase)
            .collect(Collectors.collectingAndThen(Collectors.joining(" = ?, "), it -> it += " = ?"));
        builder.append(table.get()).append(" set ").append(fields);

        Optional.ofNullable(idField).ifPresent(
            it -> builder.append(" where id = ").append(it)
        );

        String query = builder.toString();
        log.info(query);
        return query;
    }

    private static <T> String createInsertQuery(Class<T> clazz) {
        StringBuilder builder = new StringBuilder("insert into ");

        Optional<Table> table = Optional.ofNullable(clazz.getAnnotation(Table.class));
        if (table.isEmpty()) {
            return "";
        }

        String fields = Arrays.stream(clazz.getDeclaredFields())
            .map(it -> it.getAnnotation(Column.class))
            .map(Column::value)
            .map(it -> "?")
            .collect(Collectors.joining(", "));

        Table table1 = table.get();
        builder.append(table1.name()).append(" values ( next value for ").append(table1.sequence()).append(", ").append(fields).append(" )");

        String query = builder.toString();
        log.info(query);
        return query;
    }

    public static <T extends IdentifierEntity> void sendUpdateQuery(
        Class<?> clazz,
        T entity
    ) {
        sendUpdateQuery(clazz, entity, createUpdateQuery(clazz, entity.getId()));
    }

    public static <T> void sendInsertQuery(
        Class<?> clazz,
        T entity
    ) {
        sendUpdateQuery(clazz, entity, createInsertQuery(clazz));
    }

    private static <T> void sendUpdateQuery(
        Class<?> clazz,
        T entity,
        String query
    ) {
        Object[] params = Arrays.stream(clazz.getDeclaredMethods())
            .filter(it -> !Pattern.compile(IGNORED_METHODS).matcher(it.getName()).find())
            .sorted((it1, it2) -> it1.getName().compareToIgnoreCase(it2.getName()))
            .map(it -> {
                try {
                    return it.invoke(entity);
                } catch (Exception e) {
                    return null;
                }
            }).toArray();

        log.info(Arrays.toString(params));

        context.getBean(JdbcTemplate.class).update(query, params);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }
}
