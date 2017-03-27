package com.wang.sql.condition;

import com.wang.sql.field.Field;

import java.util.*;

/**
 * Created by paopao on 16/11/26.
 */
public abstract class AbstractCondition implements Condition {

    protected LinkedHashMap<Logic, Condition> conditionChain = new LinkedHashMap<>();
    protected Comparator comparator;
    protected Field field;
    protected Object expect;
    protected Collection<Object> args = new ArrayList<>();

    @Override
    public Condition and(Condition other) {
        conditionChain.put(Logic.AND, other);
        appendArgs(other);
        return this;
    }

    @Override
    public Condition or(Condition other) {
        conditionChain.put(Logic.OR, other);
        appendArgs(other);
        return this;
    }

    @Override
    public Collection<Object> getArgs() {
        return args;
    }

    private void appendArgs(Condition condition) {
        args.addAll(condition.getArgs());
    }

    @Override
    public String getSQL() {
        StringBuilder sb = new StringBuilder();
        sb.append(field.toString());
        sb.append(" ");
        sb.append(comparator.getComparator());
        sb.append(" ? ");
        sb.append(getConditionChainSQL());
        return sb.toString();
    }

    protected String getConditionChainSQL() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<Logic, Condition> entry : conditionChain.entrySet()) {
            sb.append(" ");
            sb.append(entry.getKey());
            sb.append(" ");
            sb.append(entry.getValue().getSQL());
        }
        return sb.toString();
    }
}
