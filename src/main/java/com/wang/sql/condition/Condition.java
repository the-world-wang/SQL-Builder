package com.wang.sql.condition;

/**
 * Created by paopao on 16/11/26.
 */
public interface Condition {

    Condition and(Condition other);

    Condition or(Condition other);
}
