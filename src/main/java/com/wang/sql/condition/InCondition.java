package com.wang.sql.condition;

import com.wang.sql.QueryBuilder;
import com.wang.sql.field.Field;

import java.util.Collection;

/**
 * Created by paopao on 16/11/26.
 */
public class InCondition extends AbstractCondition {

    private Collection<Object> values;
    private QueryBuilder query;

    public InCondition(Field field, Collection<Object> values) {
        this.field = field;
        this.values = values;
        this.args.addAll(values);
    }

    public InCondition(Field field, QueryBuilder query) {
        this.field = field;
        this.query = query;
    }

    @Override
    public String getSQL() {
        StringBuilder sb = new StringBuilder();
        sb.append(field.toString());
        sb.append(" ");
        sb.append(Comparator.in.getComparator());
        sb.append("(");
        if (query != null) {
            sb.append(query.getSQL());
        } else {
            for (Object value : values) {
                sb.append(String.valueOf(value));
                sb.append(",");
            }
            sb.delete(sb.length() - 1, sb.length());
        }
        sb.append(")");
        sb.append(getConditionChainSQL());
        return sb.toString();
    }
}
