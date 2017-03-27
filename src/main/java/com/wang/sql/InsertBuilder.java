package com.wang.sql;

import com.google.common.base.Joiner;
import com.google.common.base.Predicate;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * Created by wanghao on 17/1/15.
 */
public class InsertBuilder {

    private String tableName;
    private Object obj;
    private String insertSQL;
    private boolean isCamelCase = false;
    private Set<String> include;
    private Set<String> exclude;
    private Collection<Object> args = new ArrayList<>();

    public InsertBuilder(Object obj) {
        this.obj = obj;
        this.tableName = obj.getClass().getSimpleName().toLowerCase();
    }

    public static InsertBuilder insertInto(Object obj) {
        return new InsertBuilder(obj);
    }

    public InsertBuilder withCamelCase(boolean isCamelCase) {
        this.isCamelCase = isCamelCase;
        return this;
    }

    public InsertBuilder include(Set<String> include) {
        this.include = include;
        return this;
    }

    public InsertBuilder exclude(Set<String> exclude) {
        this.exclude = exclude;
        return this;
    }

    public String getSQL() {
        if (insertSQL == null) {
            StringBuilder sb = new StringBuilder("insert into `");
            sb.append(tableName);
            sb.append("`(");
            sb = convertFromBean(sb);
            sb.append(")");
            insertSQL = sb.toString();
        }
        return insertSQL;
    }

    private StringBuilder convertFromBean(StringBuilder sb) {
        Map<String, Object> model = JsonUtils.convertToMap(obj);
        Set<String> allKeys = model.keySet();
        Set<String> keys = allKeys;
        if (exclude != null) {
            keys = Sets.difference(allKeys, exclude);
        }
        if (include != null) {
            keys = Sets.intersection(allKeys, include);
        }
        if (isCamelCase) {
            // TODO isCamelCase
        }
        Map<String, Object> argsMap = Maps.filterKeys(model, new InsertPredicate<>(keys));
        args.addAll(argsMap.values());
        String join = Joiner.on(",").join(keys);
        sb.append(join).append(") values(");
        int size = keys.size();
        for (int i = 0; i < size; i++) {
            sb.append("?,");
            if (i == size - 1) {
                sb.replace(sb.lastIndexOf(","), sb.lastIndexOf(",") + 1, "");
            }
        }
        return sb;
    }

    public Object[] getArgs() {
        return args.toArray();
    }

    public static class InsertPredicate<T> implements Predicate<T> {

        private Set<T> keys;

        private InsertPredicate(Set<T> keys) {
            this.keys = keys;
        }

        @Override
        public boolean apply(T input) {
            return keys.contains(input);
        }

        @Override
        public boolean test(T input) {
            return apply(input);
        }
    }
}
