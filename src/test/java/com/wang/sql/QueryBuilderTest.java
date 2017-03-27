package com.wang.sql;

import com.wang.sql.field.Sort;
import org.junit.Test;

import java.util.Arrays;

import static com.wang.sql.QueryBuilder.select;
import static com.wang.sql.field.Field.distinct;
import static com.wang.sql.field.Field.field;

/**
 * Created by paopao on 16/11/26.
 */
public class QueryBuilderTest {

    @Test
    public void testSelect() {
        QueryBuilder builder = select("id", "name")
                .from("merchant")
                .where(field("sn").is("1111111").and(field("id").is("123456")))
                .orderBy("ctime", Sort.DESC)
                .limit(5, 10);
        String SQL1 = builder.getSQL();
        Object[] args = builder.getArgs();

        QueryBuilder builder2 = select(distinct("name"))
                .from("merchant")
                .where(field("sn").is("123456")
                        .and(field("id").is("123456")).and(field("name").like("123"))
                        .or(field("name").gte("123"))
                        .or(field("name").isNull()).and(field("sn").in(Arrays.asList("1", "2"))))
                .groupBy("name")
                .orderBy("ctime", Sort.DESC)
                .limit(5, 10);
        String SQL2 = builder2.getSQL();
        Object[] args2 = builder2.getArgs();
        System.out.println(SQL1);
        System.out.println(SQL2);
    }

    @Test
    public void testIn() {
        QueryBuilder builder = select("*")
                .from("store")
                .where(field("merchant_id").in(
                        select("id").from("merchant").where(field("name").isNull())));
        Object[] args = builder.getArgs();
        System.out.println(builder.getSQL());
    }

    @Test
    public void testJoin() {
        QueryBuilder builder = select("*")
                .from(field("store").as("s"))
                .join(field("merchant").as("m")).on(field("s.id").is("m.id"))
                .join(field("terminal").as("t")).on(field("t.id").is("m.id"))
                .where(field("t.id").is("1"));
        String SQL = builder.getSQL();
        Object[] args = builder.getArgs();
        System.out.println(args);
    }
}
