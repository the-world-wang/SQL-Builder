package com.wang.sql;

import com.wang.sql.mode.Foo;
import org.junit.Test;

import static com.wang.sql.UpdateBuilder.update;
import static com.wang.sql.field.Field.field;

/**
 * Created by paopao on 17/1/16.
 */
public class UpdateBuilderTest {

    @Test
    public void testUpdate() {
        System.out.println(update("foo")
                .withClass(Foo.class)
                .where(field("id").is("name"))
                .getSQL());
    }

    @Test
    public void testUpdate2(){
        update("foo").set
    }
}
