package com.wang.sql;

import com.wang.sql.condition.Condition;
import com.wang.sql.condition.IsCondition;

/**
 * Created by paopao on 16/11/22.
 */
public class Field {

    protected String name;
    protected String alias;

    public static Field field(String name) {
        Field field = new Field();
        field.name = name;
        return field;
    }

    public Field as(String alias) {
        this.alias = alias;
        return this;
    }

    public Condition is(String str) {
        return new IsCondition(this, str);
    }

    public String toString() {
        if (alias == null) {
            return name;
        }
        return name + " as " + alias;
    }
}
