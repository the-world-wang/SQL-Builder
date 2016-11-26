package com.wang.sql.condition;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by paopao on 16/11/26.
 */
public abstract class AbstractCondition implements Condition {

    protected Map<Logic, Condition> conditionChain = new HashMap<>();

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
        for (Map.Entry<Logic, Condition> entry : conditionChain.entrySet()) {
            sb.append(" ");
            sb.append(entry.getKey());
            sb.append(" ");
            sb.append(entry.getValue());
        }
        return sb.toString();
    }
}
