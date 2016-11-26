package com.wang.sql.condition;

import com.wang.sql.field.Field;

/**
 * Created by paopao on 16/11/26.
 */
public class IsNullCondition extends AbstractCondition {

    public IsNullCondition(Field field) {
        this.field = field;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(field.getName());
        sb.append(" ");
        sb.append(Comparator.isNull.getComparator());
        sb.append(getConditionChainSQL());
        return sb.toString();
    }
}
