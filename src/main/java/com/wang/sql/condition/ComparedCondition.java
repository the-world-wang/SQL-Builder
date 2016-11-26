package com.wang.sql.condition;

import com.wang.sql.field.Field;

/**
 * Created by paopao on 16/11/26.
 */
public class ComparedCondition extends AbstractCondition {

    public ComparedCondition(Field field, String expect, Comparator comparator) {
        this.field = field;
        this.expect = expect;
        this.comparator = comparator;
    }
}
