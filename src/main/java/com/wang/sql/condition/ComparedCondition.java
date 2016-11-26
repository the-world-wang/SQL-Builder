package com.wang.sql.condition;

import com.wang.sql.field.Field;

/**
 * Created by paopao on 16/11/26.
 */
public class ComparedCondition extends AbstractCondition {

    public ComparedCondition(Field field, String expect, Operator operator) {
        this.field = field;
        this.expect = expect;
        this.operator = operator;
    }
}
