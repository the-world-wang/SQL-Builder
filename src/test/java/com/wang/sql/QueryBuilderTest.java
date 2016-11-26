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
        String result = select("*")
                .from("merchant")
                .where(field("sn").equals("123456").and(field("id").equals("123456")))
                .orderBy("ctime", Sort.DESC)
                .limit(5, 10).getSQL();

        String result2 = select(distinct("name"))
                .from("merchant")
                .where(field("sn").equals("123456")
                        .and(field("id").equals("123456")).and(field("name").like("123"))
                        .or(field("name").gte("123"))
                        .or(field("name").isNull()).and(field("sn").in(Arrays.asList("1", "2"))))
                .groupBy("name")
                .orderBy("ctime", Sort.DESC)
                .limit(5, 10).getSQL();
        System.out.println(result);
        System.out.println(result2);
    }

    @Test
    public void testIn() {
        String sql = select("*")
                .from("store")
                .where(field("merchant_id").in(
                        select("id").from("merchant").where(field("name").isNull())))
                .getSQL();
        System.out.println(sql);
    }

    @Test
    public void testJoin() {
        String sql = select("*")
                .from(field("store").as("s"))
                .join(field("merchant").as("m")).on(field("s.id").equals("m.id"))
                .join(field("terminal").as("t")).on(field("t.id").equals("m.id"))
                .where(field("t.id").equals("1"))
                .getSQL();
        System.out.println(sql);
    }
}
