package com.wang.sql;

import com.google.common.base.Joiner;
import com.wang.sql.annotation.Table;
import com.wang.sql.condition.Condition;

import java.util.*;

/**
 * Created by paopao on 17/1/16.
 */
public class UpdateBuilder {

    private String tableName;
    private Object obj;
    private List<Condition> conditions = null;
    private List<String> fields;
    private Collection<Object> args = new ArrayList<>();

    public UpdateBuilder(Object obj) {
        this.obj = obj;
        Table table = obj.getClass().getAnnotation(Table.class);
        if (table == null) {
            tableName = obj.getClass().getSimpleName().toLowerCase();
        } else {
            tableName = table.name();
        }
        args.addAll(JsonUtils.convertToMap(obj).values());
    }

    public static UpdateBuilder update(Object obj) {
        return new UpdateBuilder(obj);
    }

    public UpdateBuilder set(String field) {
        if (this.fields == null) {
            this.fields = new ArrayList<>();
        }
        this.fields.add(field);
        return this;
    }

    public UpdateBuilder where(Condition... conditions) {
        if (this.conditions == null) {
            this.conditions = new ArrayList<>();
        }
        Collections.addAll(this.conditions, conditions);
        for (Condition condition : conditions) {
            args.addAll(condition.getArgs());
        }
        return this;
    }

    public String getSQL() {
        StringBuilder sb = new StringBuilder();
        sb.append("update ");
        sb.append(tableName);
        sb.append(" set ");
        Map<String, Object> model = JsonUtils.convertToMap(obj);
        String join = Joiner.on("=?,").join(model.keySet());
        sb.append(join).append("=?");
        if (conditions != null) {
            sb.append(" where ");
            for (Condition condition : conditions) {
                sb.append(condition.getSQL());
            }
        }
        return sb.toString();
    }

    public Object[] getArgs() {
        return args.toArray();
    }
}
