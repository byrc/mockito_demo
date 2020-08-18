package com.demo.dao;

import com.demo.model.PageParam;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

public interface JdbcQueryManager {
    <E> Optional<E> queryForObject(Object var1, Class<E> var2);

    <E> List<E> queryForList(Object var1, Class<E> var2);

    <E> List<E> queryForPageList(Object var1, Class<E> var2, PageParam var3);

    <E, R> List<R> queryForList(Object var1, Class<E> var2, Function<E, R> var3);

    List<Map<String, Object>> queryForMap(Map<String, Object> var1);
}
