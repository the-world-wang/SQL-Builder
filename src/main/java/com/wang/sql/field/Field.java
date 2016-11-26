package com.wang.sql.field;

import com.wang.sql.condition.ComparedCondition;
import com.wang.sql.condition.Condition;
import com.wang.sql.condition.IsNullCondition;
import com.wang.sql.condition.Operator;

/**
 * Created by paopao on 16/11/22.
 */
public class Field {

    protected String name;
    protected String alias;
    private boolean isDistinct;

    public static Field field(String name) {
        Field field = new Field();
        field.name = name;
        return field;
    }

    public static Field distinct(String name) {
        Field field = field(name);
        field.isDistinct = true;
        return field;
    }

    public Field as(String alias) {
        this.alias = alias;
        return this;
    }

    public String getName() {
        return name;
    }

    public Condition is(String str) {
        return new ComparedCondition(this, str, Operator.equal);
    }

    public Condition gt(String str) {
        return new ComparedCondition(this, str, Operator.gt);
    }

    public Condition gte(String str) {
        return new ComparedCondition(this, str, Operator.gte);
    }

    public Condition lt(String str) {
        return new ComparedCondition(this, str, Operator.lt);
    }

    public Condition lte(String str) {
        return new ComparedCondition(this, str, Operator.lte);
    }

    public Condition like(String str) {
        return new ComparedCondition(this, str, Operator.like);
    }

    public Condition isNull() {
        return new IsNullCondition(this);
    }

    public String toString() {
        if (isDistinct) {
            return String.format("DISTINCT(%s)", alias != null ? alias : name);
        }
        if (alias == null) {
            return name;
        }
        return name + " as " + alias;
    }
}