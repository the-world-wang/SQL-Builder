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

    public Condition eq(Object obj) {
        return new ComparedCondition(this, obj, Comparator.equals);
    }

    public Condition notEquals(Object obj) {
        return new ComparedCondition(this, obj, Comparator.notEquals);
    }

    public Condition gt(Object obj) {
        return new ComparedCondition(this, obj, Comparator.gt);
    }

    public Condition gte(Object obj) {
        return new ComparedCondition(this, obj, Comparator.gte);
    }

    public Condition lt(Object obj) {
        return new ComparedCondition(this, obj, Comparator.lt);
    }

    public Condition lte(Object obj) {
        return new ComparedCondition(this, obj, Comparator.lte);
    }

    public Condition like(Object obj) {
        return new ComparedCondition(this, obj, Comparator.like);
    }

    public Condition notLike(Object obj) {
        return new ComparedCondition(this, obj, Comparator.notLike);
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
