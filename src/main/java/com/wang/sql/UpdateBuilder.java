package com.wang.sql;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.collect.Sets;
import com.wang.sql.condition.Condition;

import java.util.*;

/**
 * Created by paopao on 17/1/16.
 */
public class UpdateBuilder {

    private String tableName;
    private Class clazz;
    private List<Condition> conditions = null;
    private List<String> fields;

    public UpdateBuilder(String tableName) {
        this.tableName = tableName;
    }

    public static UpdateBuilder update(String tableName) {
        return new UpdateBuilder(tableName);
    }

    public UpdateBuilder withClass(Class clazz) {
        this.clazz = clazz;
        return this;
    }

    public UpdateBuilder set(String field) {
        if (this.fields == null) {
            this.fields = new ArrayList<>();
        }
        this.fields.add(field);
    }

    public UpdateBuilder where(Condition... conditions) {
        if (this.conditions == null) {
            this.conditions = new ArrayList<>();
        }
        Collections.addAll(this.conditions, conditions);
        return this;
    }

    public String getSQL() {
        StringBuilder sb = new StringBuilder();
        sb.append("update ");
        sb.append(tableName);
        sb.append(" set ");
        Object value = null;
        try {
            value = clazz.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Map<String, Object> result = JsonUtils.mapper.convertValue(value, new TypeReference<Map<String, Object>>() {
        });
        Set<String> allKeys = result.keySet();
        int index = 0;
        int keySize = allKeys.size();
        for (String key : allKeys) {
            sb.append(key);
            sb.append(" =?");
            if (index < keySize - 1) {
                sb.append(",");
            }
            index++;
        }
        if (conditions != null) {
            sb.append(" where ");
            for (Condition condition : conditions) {
                sb.append(condition.toString());
            }
        }
        return sb.toString();
    }
}
