package com.wang.sql.condition;

/**
 * Created by paopao on 16/11/26.
 */
public enum Operator {
    equal("="), gt(">"), gte(">="), lt("<"), lte("<="), like("like"), isNull("IS NULL");

    Operator(String operator) {
        this.operator = operator;
    }

    private String operator;

    public String getOperator() {
        return operator;
    }
}
