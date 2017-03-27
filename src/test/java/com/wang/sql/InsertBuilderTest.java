package com.wang.sql;

import com.google.common.collect.Sets;
import com.wang.sql.model.Foo;
import org.junit.Test;

import static com.wang.sql.InsertBuilder.*;

/**
 * Created by paopao on 17/1/15.
 */
public class InsertBuilderTest {

    @Test
    public void testInsert() {
        Foo foo = new Foo();
        InsertBuilder builder = insertInto(foo)
                .withCamelCase(true)
//                .include(Sets.newHashSet("name"))
                .exclude(Sets.newHashSet("name", "aa"));
        System.out.println(builder.getSQL());
    }
}
