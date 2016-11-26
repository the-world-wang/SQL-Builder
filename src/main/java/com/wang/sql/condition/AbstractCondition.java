package com.wang.sql.condition;

import com.wang.sql.field.Field;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by paopao on 16/11/26.
 */
public abstract class AbstractCondition implements Condition {

    protected Map<Logic, Condition> conditionChain = new HashMap<>();
    protected Comparator comparator;
    protected Field field;
    protected String expect;

    @Override
    public Condition and(Condition other) {
        conditionChain.put(Logic.AND, other);
        return this;
    }

    @Override
    public Condition or(Condition other) {
        conditionChain.put(Logic.OR, other);
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(field.toString());
        sb.append(" ");
        sb.append(comparator.getComparator());
        sb.append(" ");
        sb.append(expect);
        sb.append(getConditionChainSQL());
        return sb.toString();
    }

    protected String getConditionChainSQL() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<Logic, Condition> entry : conditionChain.entrySet()) {
            sb.append(" ");
            sb.append(entry.getKey());
            sb.append(" ");
            sb.append(entry.getValue());
        }
        return sb.toString();
    }
}
