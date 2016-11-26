package com.wang.sql.aggregate;

import com.wang.sql.field.Field;

/**
 * Created by paopao on 16/11/26.
 */
public class AggregateFunction<T> extends Field {

    public static AggregateFunction count() {
        AggregateFunction aggregate = new AggregateFunction();
        return aggregate;
    }
}
