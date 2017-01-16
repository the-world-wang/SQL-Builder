package com.wang.sql;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.collect.Sets;

import java.util.Map;
import java.util.Set;

import static com.google.common.base.CaseFormat.*;

/**
 * Created by wanghao on 17/1/15.
 */
public class InsertBuilder {

    private String tableName;
    private Class clazz;
    private String insertSQL;
    private boolean isCamelCase = false;
    private Set<String> include;
    private Set<String> exclude;

    public InsertBuilder(String tableName) {
        this.tableName = tableName;
    }

    public static InsertBuilder insertInto(String tableName) {
        return new InsertBuilder(tableName);
    }

    public InsertBuilder withClass(Class clazz) {
        this.clazz = clazz;
        return this;
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
        Object value = null;
        try {
            value = clazz.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Map<String, Object> result = JsonUtils.mapper.convertValue(value, new TypeReference<Map<String, Object>>() {
        });
        Set<String> allKeys = result.keySet();
        Set<String> keys = null;
        if (exclude != null) {
            keys = Sets.difference(allKeys, exclude);
        }
        if (include != null) {
            keys = Sets.intersection(allKeys, include);
        }
        if (keys == null) {
            keys = allKeys;
        }
        int index = 0;
        int entrySize = keys.size();
        for (String key : keys) {
            if (isCamelCase) {
                sb.append(LOWER_CAMEL.to(LOWER_UNDERSCORE, key));
            } else {
                sb.append(key);
            }
            if (index < (entrySize - 1)) {
                sb.append(",");
            }
            index++;
        }
        sb.append(") values(");
        for (int i = 0; i < index; i++) {
            sb.append("?,");
            if (i == index - 1) {
                sb.replace(sb.lastIndexOf(","), sb.lastIndexOf(",") + 1, "");
            }
        }
        return sb;
    }
}
