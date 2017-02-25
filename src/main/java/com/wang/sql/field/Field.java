package com.wang.sql.field;

import com.wang.sql.QueryBuilder;
import com.wang.sql.condition.*;

import java.util.Collection;

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
        return new ComparedCondition(this, str, Comparator.equals);
    }

    public Condition notEquals(String str) {
        return new ComparedCondition(this, str, Comparator.notEquals);
    }

    public Condition gt(String str) {
        return new ComparedCondition(this, str, Comparator.gt);
    }

    public Condition gte(String str) {
        return new ComparedCondition(this, str, Comparator.gte);
    }

    public Condition lt(String str) {
        return new ComparedCondition(this, str, Comparator.lt);
    }

    public Condition lte(String str) {
        return new ComparedCondition(this, str, Comparator.lte);
    }

    public Condition like(String str) {
        return new ComparedCondition(this, str, Comparator.like);
    }

    public Condition notLike(String str) {
        return new ComparedCondition(this, str, Comparator.notLike);
    }

    public Condition isNull() {
        return new IsNullCondition(this);
    }

    public Condition in(Collection values) {
        return new InCondition(this, values);
    }

    public Condition in(QueryBuilder query) {
        return new InCondition(this, query);
    }

    public String toString() {
        if (isDistinct) {
            return String.format("DISTINCT(%s)", alias != null ? alias : name);
        }
        if (alias == null) {
            return name;
        }
        return name + " " + alias;
    }
}
