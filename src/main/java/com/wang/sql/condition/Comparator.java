package com.wang.sql.condition;

/**
 * Created by paopao on 16/11/26.
 */
public enum Comparator {
    equals("="), notEquals("<>"), gt(">"), gte(">="), lt("<"), lte("<="), like("like"), notLike("not like"), isNull("IS NULL"), in("in");

    Comparator(String comparator) {
        this.comparator = comparator;
    }

    private String comparator;

    public String getComparator() {
        return comparator;
    }
}
