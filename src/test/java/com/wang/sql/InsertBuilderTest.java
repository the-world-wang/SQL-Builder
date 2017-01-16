package com.wang.sql;

import com.google.common.collect.Sets;
import com.wang.sql.mode.Foo;
import org.junit.Test;

import static com.wang.sql.InsertBuilder.*;

/**
 * Created by paopao on 17/1/15.
 */
public class InsertBuilderTest {

    @Test
    public void testInsert() {
        insertInto("foo")
                .withClass(Foo.class)
                .withCamelCase(true)
                .include(Sets.newHashSet("name"))
                .exclude(Sets.newHashSet("name", "aa"))
                .getSQL();
    }
}
