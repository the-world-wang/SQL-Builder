package com.wang.sql.condition;

import java.util.Collection;

/**
 * Created by paopao on 16/11/26.
 */
public interface Condition {

    Condition and(Condition other);

    Condition or(Condition other);

    Collection<Object> getArgs();

    String getSQL();
}
