package com.wang.sql;

import com.wang.sql.condition.Condition;
import com.wang.sql.field.Field;

/**
 * Created by paopao on 16/11/26.
 */
public class JoinBuilder {

    private QueryBuilder query;
    private Field field;
    private Condition condition;

    JoinBuilder(QueryBuilder query, Field field) {
        this.query = query;
        this.field = field;
    }

    public QueryBuilder on(Condition condition) {
        this.condition = condition;
        return this.query;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(field.toString());
        sb.append(" on ");
        sb.append(condition.toString());
        return sb.toString();
    }
}
