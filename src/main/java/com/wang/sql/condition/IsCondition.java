package com.wang.sql.condition;

import com.wang.sql.Field;

/**
 * Created by paopao on 16/11/26.
 */
public class IsCondition extends AbstractCondition {

    private static final String EQUAL = " = ";
    private Field field;
    private String expect;

    public IsCondition(Field field, String expect) {
        this.field = field;
        this.expect = expect;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(field.toString());
        sb.append(EQUAL);
        sb.append(expect);
        sb.append(super.toString());
        return sb.toString();
    }
}
