package com.wang.sql;

import com.wang.sql.model.Foo;
import org.junit.Test;

import static com.wang.sql.UpdateBuilder.update;
import static com.wang.sql.field.Field.field;

/**
 * Created by paopao on 17/1/16.
 */
public class UpdateBuilderTest {

    @Test
    public void testUpdate() {
        Foo foo = new Foo();
        UpdateBuilder builder = update(foo)
                .where(field("id").is("1"));
        String SQL = builder.getSQL();
        Object[] args = builder.getArgs();
        System.out.println(SQL);
    }

    @Test
    public void testUpdate2() {
    }
}
